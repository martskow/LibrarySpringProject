package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.*;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.infrastructure.repository.LoanRepository;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.LoanErrors.LoanNotFoundException;
import com.example.technologiesieciowe.service.error.BookErrors.NotAvailableCopiesException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing loans.
 */
@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanArchiveService loanArchiveService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    /**
     * Constructs a new instance of LoanService.
     * @param loanRepository The repository for loans.
     * @param loanArchiveService The service for managing loan archives.
     * @param bookRepository The repository for books.
     * @param userRepository The repository for users.
     * @param bookService The service for managing books.
     */
    @Autowired
    public LoanService(LoanRepository loanRepository, LoanArchiveService loanArchiveService, BookRepository bookRepository, UserRepository userRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.loanArchiveService = loanArchiveService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    /**
     * Retrieves all loans.
     * @return A list of all loans.
     */
    public List<LoanEntity> getAll() {
        return loanRepository.findAll();
    }

    /**
     * Deletes a loan by its ID.
     * @param id The ID of the loan to delete.
     * @throws LoanNotFoundException If the loan with the specified ID is not found.
     */
    public void delete(Integer id) {
        Optional<LoanEntity> loanOptional = loanRepository.findById(id);
        if (loanOptional.isPresent()) {
            loanRepository.deleteById(id);
        } else {
            throw LoanNotFoundException.create(id.toString());
        }
    }

    /**
     * Adds a loan.
     * @param loan The loan entity to add.
     * @return The added loan entity.
     * @throws FieldRequiredException If any required field is null.
     * @throws BookNotFoundException If the book with the specified ID is not found.
     * @throws NotAvailableCopiesException If the book is not available for loan.
     */
    public LoanEntity addLoan(LoanEntity loan) {
        if (loan.getBook() == null || loan.getBook().getId() == null) {
            throw FieldRequiredException.create("Book ID");
        } else if (loan.getUser() == null || loan.getUser().getUserId() == null) {
            throw FieldRequiredException.create("User ID");
        }

        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now().toString());
        }
        validateLoanDate(loan.getLoanDate());
        if (loan.getDueDate() != null) {
            validateLoanDueDate(loan.getDueDate(), loan.getLoanDate());
        } else {
            LocalDate loanDate = LocalDate.parse(loan.getLoanDate(), DateTimeFormatter.ISO_DATE);
            LocalDate dueDate = loanDate.plusMonths(3);
            String dueDateStr = dueDate.format(DateTimeFormatter.ISO_DATE);
            loan.setDueDate(dueDateStr);
        }

        Optional<BookEntity> bookOptional = bookRepository.findById(loan.getBook().getId());
        if (bookOptional.isPresent()) {
            BookEntity book = bookOptional.get();
            if (bookService.isAvailable(book.getId())) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);
            } else {
                throw NotAvailableCopiesException.create(book.getTitle());
            }
        } else {
            throw BookNotFoundException.create(loan.getBook().getId().toString());
        }

        return loanRepository.save(loan);
    }

    /**
     * Retrieves a specific loan by its ID.
     * @param loanId The ID of the loan to retrieve.
     * @return The loan entity.
     * @throws LoanNotFoundException If the loan with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to access the loan.
     */
    public LoanEntity getOne(Integer loanId) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (loan.getUser().getUserId().toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this loan.");
        } else {
            return loan;
        }
    }

    /**
     * Extends the due date of a loan.
     * @param loanId The ID of the loan to extend the due date.
     * @param editedLoan The edited loan entity containing the new due date.
     * @return The updated loan entity.
     * @throws LoanNotFoundException If the loan with the specified ID is not found.
     */
    public LoanEntity extendDueDateLoan(Integer loanId, LoanEntity editedLoan) {
        LoanEntity loanToEdit = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));

        String newDueDate = editedLoan.getDueDate();

        if (newDueDate != null) {
            validateLoanDueDate(newDueDate, loanToEdit.getLoanDate());
            loanToEdit.setDueDate(newDueDate);
        }

        return loanRepository.save(loanToEdit);
    }

    /**
     * Processes the return of a loan.
     * @param loanId The ID of the loan to return.
     * @param endedLoan The loan entity containing the return date.
     * @throws LoanNotFoundException If the loan with the specified ID is not found.
     * @throws BookNotFoundException If the book associated with the loan is not found.
     */
    public void returnBook(Integer loanId, LoanEntity endedLoan) {
        LoanEntity loanToEdit = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));

        LoanArchiveEntity archivalLoan = new LoanArchiveEntity();
        if (endedLoan.getReturnDate() == null) {
            archivalLoan.setReturnDate(LocalDate.now().toString());
        } else {
            archivalLoan.setReturnDate(endedLoan.getReturnDate());
        }
        archivalLoan.setBook(loanToEdit.getBook());
        archivalLoan.setUser(loanToEdit.getUser());
        archivalLoan.setLoanDate(loanToEdit.getLoanDate());
        archivalLoan.setDueDate(loanToEdit.getDueDate());

        loanArchiveService.addLoanArchive(archivalLoan);
        loanRepository.deleteById(loanId);
    }

    /**
     * Validates the loan date.
     * @param loanDate The loan date to validate.
     * @throws InvalidDateException If the loan date is invalid.
     */
    private void validateLoanDate(String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid loan date format");
        }
        if (parsedReviewDate.isBefore(currentDate)) {
            throw InvalidDateException.create("Loan date cannot be in the past");
        }
    }

    /**
     * Validates the loan due date.
     * @param dueDate The due date to validate.
     * @param loanDate The loan date to compare with.
     * @throws InvalidDateException If the due date is invalid.
     */
    private void validateLoanDueDate(String dueDate, String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;
        LocalDate parsedLoanDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);

        try {
            parsedReviewDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid due date format");
        }
        if (parsedReviewDate.isBefore(currentDate)) {
            throw InvalidDateException.create("Loan due date cannot be in the past");
        }
        if (parsedReviewDate.isBefore(parsedLoanDate)) {
            throw InvalidDateException.create("Loan due date cannot be before loan date");
        }
    }

    /**
     * Checks if a user has borrowed a specific book.
     * @param userId The ID of the user.
     * @param bookId The ID of the book.
     * @return True if the user has borrowed the book, otherwise false.
     */
    public boolean hasUserBorrowedBook(Integer userId, Integer bookId) {
        return loanRepository.existsByUserAndBook(userRepository.findById(userId), bookRepository.findById(bookId));
    }

    /**
     * Retrieves the loans for a specific book.
     *
     * @param bookId the ID of the book
     * @return
     */
    public Iterable<LoanEntity> getLoanByBook(Integer bookId) {
        return loanRepository.findByBookId(bookId);
    }

    /**
     * Retrieves the loans for a specific user.
     * Access is restricted based on user role.
     *
     * @param userId the ID of the user
     * @throws UserAccessDeniedException if the user does not have permission to access the loans
     */
    public Iterable<LoanEntity> getLoanByUser(Integer userId) {
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (userId.toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this loans.");
        } else {
            return loanRepository.findByUserUserId(userId);
        }
    }

    /**
     * Retrieves all delayed loans where the due date is before today's date.
     *
     * @return An iterable collection of LoanEntity objects representing delayed loans.
     */
    public Iterable<LoanEntity> getDelays() {
        String today = LocalDate.now().toString();
        return loanRepository.findByDueDateBefore(today);
    }

    /**
     * Retrieves all delayed loans where the due date is before today's date.
     * @param userId the ID of the user
     * @return An iterable collection of LoanEntity objects representing delayed loans.
     * @throws UserAccessDeniedException when user is not allowed to get information abitu this loans
     */
    public Iterable<LoanEntity> getUserDelays(Integer userId) {
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (userId.toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this delays.");
        } else {
            String today = LocalDate.now().toString();
            return loanRepository.findByDueDateBeforeAndUserUserId(today, userId);
        }
    }
}
