package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for handling book-related HTTP requests.
 */
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    /**
     * Constructor injection for BookController.
     * @param bookService The BookService instance to be injected.
     */
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    /**
     * Endpoint for adding a new book.
     * @param book The BookEntity object representing the new book.
     * @return The added BookEntity object.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookEntity addBook(@RequestBody BookEntity book){
        return bookService.addBook(book);
    }

    /**
     * Endpoint for editing an existing book.
     * @param id The ID of the book to edit.
     * @param editedBook The BookEntity object containing the updated book information.
     * @return The updated BookEntity object.
     */
    @PutMapping("/edit/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity editBook(@PathVariable Integer id, @RequestBody BookEntity editedBook) {
        return bookService.editBook(id, editedBook);
    }

    /**
     * Endpoint for retrieving all books.
     * @return Iterable collection of all BookEntity objects.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<BookEntity> getAllBooks(){
        return bookService.getAll();
    }

    /**
     * Endpoint for retrieving a single book by ID.
     * @param id The ID of the book to retrieve.
     * @return The BookEntity object with the specified ID.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity getOne (@PathVariable Integer id) {
        return bookService.getOne(id);
    }

    /**
     * Endpoint for deleting a book by ID.
     * @param id The ID of the book to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }

    /**
     * Endpoint for checking if a book is available.
     * @param id The ID of the book to check.
     * @return True if the book is available, false otherwise.
     */
    @GetMapping("/isAvailable/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Boolean isAvailable(@PathVariable Integer id){
        return bookService.isAvailable(id);
    }

    /**
     * Endpoint for retrieving all book titles with their authors.
     * @return List of strings representing book titles with authors.
     */
    @GetMapping("/getAllTitles")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody List<String> getAllTitles() {
        return bookService.getAllTitlesWithAuthors();
    }
}