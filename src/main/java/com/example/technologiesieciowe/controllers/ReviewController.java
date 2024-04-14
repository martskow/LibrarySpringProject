package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.service.BookService;
import com.example.technologiesieciowe.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for handling review-related HTTP requests.
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * Constructor injection for ReviewController.
     * @param reviewService The ReviewService instance to be injected.
     */
    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    /**
     * Endpoint for adding a new review.
     * @param review The ReviewEntity object representing the new review.
     * @return The added ReviewEntity object.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReviewEntity addReview(@RequestBody ReviewEntity review){
        return reviewService.addReview(review);
    }

    /**
     * Endpoint for retrieving all reviews.
     * @return Iterable collection of all ReviewEntity objects.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<ReviewEntity> getAllBooks(){
        return reviewService.getAll();
    }

    /**
     * Endpoint for retrieving a single review by ID.
     * @param id The ID of the review to retrieve.
     * @return The ReviewEntity object with the specified ID.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity getOne (@PathVariable Integer id) {
        return reviewService.getOne(id);
    }

    /**
     * Endpoint for deleting a review by ID.
     * @param id The ID of the review to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        reviewService.delete(id);
    }

    /**
     * Endpoint for retrieving all reviews for a specific book.
     * @param bookId The ID of the book for which to retrieve reviews.
     * @return Iterable collection of ReviewEntity objects for the specified book.
     */
    @GetMapping("/getByBook/{bookId}")
    public Iterable<ReviewEntity> getReviewsByBook(@PathVariable Integer bookId) {
        return reviewService.getReviewsByBook(bookId);
    }

    /**
     * Endpoint for retrieving all reviews by a specific user.
     * @param userId The ID of the user for which to retrieve reviews.
     * @return Iterable collection of ReviewEntity objects by the specified user.
     */
    @GetMapping("/getByUser/{userId}")
    public Iterable<ReviewEntity> getReviewsByUser(@PathVariable Integer userId) {
        return reviewService.getReviewsByUser(userId);
    }

    /**
     * Endpoint for editing an existing review.
     * @param id The ID of the review to edit.
     * @param editedReview The ReviewEntity object containing the updated review information.
     * @return The updated ReviewEntity object.
     */
    @PutMapping("/edit/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity editReview(@PathVariable Integer id, @RequestBody ReviewEntity editedReview) {
        return reviewService.editReview(id, editedReview);
    }
}