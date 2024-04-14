package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookDetailsRepository;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.service.error.BookDetailsErrors.BookDetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing book details.
 */
@Service
public class BookDetailsService {
    private final BookDetailsRepository bookDetailsRepository;
    private final BookRepository bookRepository;

    /**
     * Constructs a new instance of BookDetailsService.
     * @param bookDetailsRepository The repository for book details.
     */
    @Autowired
    public BookDetailsService(BookDetailsRepository bookDetailsRepository, BookRepository bookRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all book details.
     * @return A list of all book details.
     */
    public List<BookDetailsEntity> getAll() {
        return bookDetailsRepository.findAll();
    }

    /**
     * Retrieves a specific book detail by its ID.
     * @param id The ID of the book detail to retrieve.
     * @return The book detail entity.
     * @throws BookDetailsNotFoundException If the book detail with the specified ID is not found.
     */
    public BookDetailsEntity getOne(Integer id) {
        return bookDetailsRepository.findById(id).orElseThrow(() -> BookDetailsNotFoundException.create("id " + id));
    }

    /**
     * Saves a new book detail or updates an existing one.
     * @param details The book detail entity to save.
     * @return The saved book detail entity.
     */
    public BookDetailsEntity save(BookDetailsEntity details) {
        return bookDetailsRepository.save(details);
    }

    /**
     * Deletes a book detail by its ID.
     * @param id The ID of the book detail to delete.
     */
    public void delete(Integer id) {
        bookDetailsRepository.deleteById(id);
    }

    /**
     * Retrieves a list of BookEntity objects with the specified genre.
     *
     * @param genre The genre used to filter books.
     * @return A list of BookEntity objects matching the specified genre.
     */
    public List<BookEntity> getByGenre(String genre) {
        List<BookDetailsEntity> booksDetails = bookDetailsRepository.getByGenre(genre);
        List<BookEntity> books = new ArrayList<>();
        for (BookDetailsEntity bookDetails : booksDetails) {
            books.add(bookDetails.getBook());
        }
        return books;
    }

    /**
     * Retrieves a list of concatenated strings containing titles and authors of books with the specified genre.
     *
     * @param genre The genre used to filter books.
     * @return A list of strings containing concatenated titles and authors of books matching the specified genre.
     */
    public List<String> getListByGenre(String genre) {
        List<BookDetailsEntity> booksDetails = bookDetailsRepository.getByGenre(genre);
        return booksDetails.stream()
                .map(bookDetails -> bookDetails.getBook().getTitle() + ". " + bookDetails.getBook().getAuthor())
                .collect(Collectors.toList());
    }
}
