package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for handling book details-related HTTP requests.
 */
@RestController
@RequestMapping("/bookDetails")
public class BookDetailsController {
    private final BookDetailsService bookDetailsService;

    /**
     * Constructor injection for BookDetailsController.
     * @param bookDetailsService The BookDetailsService instance to be injected.
     */
    @Autowired
    public BookDetailsController(BookDetailsService bookDetailsService){
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Endpoint to add a new book details entry.
     * @param book The BookDetailsEntity representing the book details to be added.
     * @return The added BookDetailsEntity.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDetailsEntity save(@RequestBody BookDetailsEntity book){
        return bookDetailsService.save(book);
    }

    /**
     * Endpoint to retrieve all book details entries.
     * @return Iterable of BookDetailsEntity representing all book details entries.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<BookDetailsEntity> getAllBooks(){
        return bookDetailsService.getAll();
    }

    /**
     * Endpoint to retrieve a single book details entry by its ID.
     * @param id The ID of the book details entry to retrieve.
     * @return BookDetailsEntity representing the requested book details entry.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity getOne (@PathVariable Integer id) {
        return bookDetailsService.getOne(id).getBook();
    }

    /**
     * Endpoint to delete a book details entry by its ID.
     * @param id The ID of the book details entry to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        bookDetailsService.delete(id);
    }

    /**
     * Retrieves a list of BookEntity objects with the specified genre.
     *
     * @param genre The genre used to filter books.
     * @return A list of BookEntity objects matching the specified genre.
     */
    @GetMapping("/getByGenre/{genre}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody List<BookEntity> getByGenre(@PathVariable String genre) {
        return bookDetailsService.getByGenre(genre);
    }

    /**
     * Retrieves a list of book titles and authors with the specified genre.
     *
     * @param genre The genre used to filter books.
     * @return A list of strings containing book titles and authors matching the specified genre.
     */
    @GetMapping("/getListByGenre/{genre}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody List<String> getListByGenre(@PathVariable String genre) {
        return bookDetailsService.getListByGenre(genre);
    }

}

