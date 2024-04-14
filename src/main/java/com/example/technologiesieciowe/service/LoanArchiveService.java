package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanArchiveEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.infrastructure.repository.LoanArchiveRepository;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.LoanArchiveErrors.LoanArchiveNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing loan archives.
 */
@Service
public class LoanArchiveService {
    private final LoanArchiveRepository loanArchiveRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of LoanArchiveService.
     * @param loanArchiveRepository The repository for loan archives.
     * @param bookRepository The repository for books.
     * @param userRepository The repository for users.
     */
    @Autowired
    public LoanArchiveService(LoanArchiveRepository loanArchiveRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanArchiveRepository = loanArchiveRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all loan archives.
     * @return A list of all loan archives.
     */
    public List<LoanArchiveEntity> getAll() {
        return loanArchiveRepository.findAll();
    }

    /**
     * Deletes a loan archive by its ID.
     * @param id The ID of the loan archive to delete.
     * @throws LoanArchiveNotFoundException If the loan archive with the specified ID is not found.
     */
    public void delete(Integer id) {
        Optional<LoanArchiveEntity> loanOptional = loanArchiveRepository.findById(id);
        if (loanOptional.isPresent()) {
            loanArchiveRepository.deleteById(id);
        } else {
            throw LoanArchiveNotFoundException.create(id.toString());
        }
    }

    /**
     * Adds a loan archive.
     *
     * @param loan The loan archive entity to add.
     * @throws FieldRequiredException If any required field is null.
     * @throws BookNotFoundException  If the book with the specified ID is not found.
     */
    public void addLoanArchive(LoanArchiveEntity loan) {
        if (loan.getLoanDate() == null) {
            throw FieldRequiredException.create("Loan date");
        } else if (loan.getDueDate() == null) {
            throw FieldRequiredException.create("Due date");
        } else if (loan.getBook() == null) {
            throw FieldRequiredException.create("Book");
        } else if (loan.getUser() == null) {
            throw FieldRequiredException.create("User");
        } else if (loan.getReturnDate() == null) {
            throw FieldRequiredException.create("Return date");
        }
        validateLoanArchiveDate(loan.getLoanDate());
        validateLoanArchiveReturnDate(loan.getReturnDate(), loan.getLoanDate());
        validateLoanArchiveDueDate(loan.getDueDate(), loan.getLoanDate());

        Optional<BookEntity> bookOptional = bookRepository.findById(loan.getBook().getId());
        if (bookOptional.isPresent()) {
            BookEntity book = bookOptional.get();
            int availableCopies = book.getAvailableCopies();
            book.setAvailableCopies(availableCopies + 1);
            bookRepository.save(book);
        } else {
            throw BookNotFoundException.create(loan.getBook().getId().toString());
        }
        loanArchiveRepository.save(loan);
    }

    /**
     * Retrieves a specific loan archive by its ID.
     * @param loanId The ID of the loan archive to retrieve.
     * @return The loan archive entity.
     * @throws LoanArchiveNotFoundException If the loan archive with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to access the loan archive.
     */
    public LoanArchiveEntity getOne(Integer loanId) {
        LoanArchiveEntity archivalLoan = loanArchiveRepository.findById(loanId)
                .orElseThrow(() -> LoanArchiveNotFoundException.create(loanId.toString()));
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (archivalLoan.getUser().getUserId().toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this archival loan.");
        } else {
            return archivalLoan;
        }
    }

    /**
     * Validates the loan archive date.
     * @param loanDate The loan date to validate.
     * @throws InvalidDateException If the loan date is invalid.
     */
    private void validateLoanArchiveDate(String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid loan date format");
        }
        if (parsedReviewDate.isAfter(currentDate)) {
            throw InvalidDateException.create("Loan date cannot be in the future");
        }
    }

    /**
     * Validates the loan archive due date.
     * @param dueDate The due date to validate.
     * @param loanDate The loan date to compare with.
     * @throws InvalidDateException If the due date is invalid.
     */
    private void validateLoanArchiveDueDate(String dueDate, String loanDate) {
        LocalDate parsedReviewDate;
        LocalDate parsedLoanDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);

        try {
            parsedReviewDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid due date format");
        }
        if (parsedReviewDate.isBefore(parsedLoanDate)) {
            throw InvalidDateException.create("Loan due date cannot be before loan date");
        }
    }

    /**
     * Validates the loan archive return date.
     * @param returnDate The return date to validate.
     * @param loanDate The loan date to compare with.
     * @throws InvalidDateException If the return date is invalid.
     */
    private void validateLoanArchiveReturnDate(String returnDate, String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReturnDate;
        LocalDate parsedLoanDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);

        try {
            parsedReturnDate = LocalDate.parse(returnDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid return date format");
        }
        if (parsedReturnDate.isAfter(currentDate)) {
            throw InvalidDateException.create("Loan return date cannot be in the future");
        }
        if (parsedReturnDate.isBefore(parsedLoanDate)) {
            throw InvalidDateException.create("Loan return date cannot be before loan date");
        }
    }

    /**
     * Checks if a user has borrowed a specific book.
     * @param userId The ID of the user.
     * @param bookId The ID of the book.
     * @return True if the user has borrowed the book, otherwise false.
     */
    public boolean hasUserBorrowedBook(Integer userId, Integer bookId) {
        return loanArchiveRepository.existsByUserAndBook(userRepository.findById(userId), bookRepository.findById(bookId));
    }
}