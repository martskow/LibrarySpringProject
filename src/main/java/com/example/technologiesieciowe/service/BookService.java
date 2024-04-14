package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.BookErrors.IsbnAlreadyExistsException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing books.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Constructs a new instance of BookService.
     * @param bookRepository The repository for books.
     */
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books.
     * @return A list of all books.
     */
    public List<BookEntity> getAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves titles and authors of all books.
     * @return A list of strings representing titles and authors of all books.
     */
    public List<String> getAllTitlesWithAuthors() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream()
                .map(book -> book.getTitle() + ". " + book.getAuthor())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific book by its ID.
     * @param id The ID of the book to retrieve.
     * @return The book entity.
     * @throws BookNotFoundException If the book with the specified ID is not found.
     */
    public BookEntity getOne(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id.toString()));
    }

    /**
     * Checks if a book is available for borrowing.
     * @param bookId The ID of the book to check.
     * @return True if the book is available; false otherwise.
     * @throws BookNotFoundException If the book with the specified ID is not found.
     */
    public Boolean isAvailable(Integer bookId) {
        try {
            BookEntity book = bookRepository.getById(bookId);
            int availableCopies = book.getAvailableCopies();
            return availableCopies > 0;
        } catch (EntityNotFoundException e) {
            throw BookNotFoundException.create(bookId.toString());
        }
    }

    /**
     * Adds a new book.
     * @param book The book entity to add.
     * @return The added book entity.
     * @throws FieldRequiredException If any required field is null.
     * @throws IsbnAlreadyExistsException If a book with the same ISBN already exists.
     */
    public BookEntity addBook(BookEntity book) {
        if (book.getIsbn() == null) {
            throw FieldRequiredException.create("ISBN");
        } else if (book.getAuthor() == null) {
            throw FieldRequiredException.create("Author");
        } else if (book.getTitle() == null) {
            throw FieldRequiredException.create("Title");
        } else if (book.getAvailableCopies() == null) {
            throw FieldRequiredException.create("Number of available copies");
        }
        Optional<BookEntity> existingBook = Optional.ofNullable(bookRepository.getByIsbn(book.getIsbn()));
        if (existingBook.isPresent()) {
            throw IsbnAlreadyExistsException.create(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    /**
     * Edits information about a book.
     * @param bookId The ID of the book to edit.
     * @param editedBook The edited book entity.
     * @return The edited book entity.
     * @throws BookNotFoundException If the book with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not a librarian.
     */
    public BookEntity editBook(Integer bookId, BookEntity editedBook) {
        BookEntity bookToEdit = bookRepository.findById(bookId)
                .orElseThrow(() -> BookNotFoundException.create(bookId.toString()));

        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (loggedInUserRole == null || !loggedInUserRole.equals("ROLE_LIBRARIAN")) {
            throw UserAccessDeniedException.create("You are not allowed to edit information about books.");
        }

        String newIsbn = editedBook.getIsbn();
        String newTitle = editedBook.getTitle();
        String newAuthor = editedBook.getAuthor();
        String newPublisher = editedBook.getPublisher();
        String newPublishYear = editedBook.getPublishYear();
        Integer newAvailableCopies = editedBook.getAvailableCopies();

        if (newIsbn != null) {
            bookToEdit.setIsbn(newIsbn);
        }
        if (newTitle != null) {
            bookToEdit.setTitle(newTitle);
        }
        if (newAuthor != null) {
            bookToEdit.setAuthor(newAuthor);
        }
        if (newPublisher != null) {
            bookToEdit.setPublisher(newPublisher);
        }
        if (newPublishYear != null) {
            bookToEdit.setPublishYear(newPublishYear);
        }
        if (newAvailableCopies != null) {
            bookToEdit.setAvailableCopies(newAvailableCopies);
        }

        return bookRepository.save(bookToEdit);
    }

    /**
     * Deletes a book by its ID.
     * @param id The ID of the book to delete.
     * @throws BookNotFoundException If the book with the specified ID is not found.
     */
    public void delete(Integer id) {
        Optional<BookEntity> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw BookNotFoundException.create(id.toString());
        }
    }
}
