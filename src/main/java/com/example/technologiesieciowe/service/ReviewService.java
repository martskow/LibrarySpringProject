package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.repository.ReviewRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.ReviewErrors.RatingOffTheScaleException;
import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import com.example.technologiesieciowe.service.error.UserErrors.UserNotBorrowedBookException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Service class for managing reviews.
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final LoanService loanService;
    private final LoanArchiveService loanArchiveService;

    /**
     * Constructs a new instance of ReviewService.
     * @param reviewRepository The repository for reviews.
     * @param loanService The service for managing loans.
     * @param loanArchiveService The service for managing loan archives.
     */
    public ReviewService(ReviewRepository reviewRepository, LoanService loanService, LoanArchiveService loanArchiveService) {
        this.reviewRepository = reviewRepository;
        this.loanService = loanService;
        this.loanArchiveService = loanArchiveService;
    }

    /**
     * Adds a review.
     * @param review The review entity to add.
     * @return The added review entity.
     * @throws FieldRequiredException If any required field is null.
     * @throws InvalidDateException If the review date is in the future.
     * @throws UserNotBorrowedBookException If the user has not borrowed the book.
     */
    public ReviewEntity addReview(ReviewEntity review) {
        if (review.getBook() == null || review.getBook().getId() == null) {
            throw FieldRequiredException.create("Book ID");
        } else if (review.getUser() == null || review.getUser().getUserId() == null) {
            throw FieldRequiredException.create("User ID");
        } else if (review.getRating() == null) {
            throw FieldRequiredException.create("Rating");
        } else {
            if (review.getReviewDate() == null) {
                LocalDate today = LocalDate.now();
                review.setReviewDate(today.toString());
            }
        }
        validateRating(review.getRating());
        validateReviewDate(review.getReviewDate());

        if (!(loanService.hasUserBorrowedBook(review.getUser().getUserId(), review.getBook().getId())
                || loanArchiveService.hasUserBorrowedBook(review.getUser().getUserId(), review.getBook().getId()))) {
            throw UserNotBorrowedBookException.create(review.getBook().getId().toString());
        }

        return reviewRepository.save(review);
    }

    /**
     * Retrieves all reviews.
     * @return A list of all reviews.
     */
    public List<ReviewEntity> getAll() {
        return reviewRepository.findAll();
    }

    /**
     * Retrieves a specific review by its ID.
     * @param reviewId The ID of the review to retrieve.
     * @return The review entity.
     * @throws ReviewNotFoundException If the review with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to access the review.
     */
    public ReviewEntity getOne(Integer reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> ReviewNotFoundException.create(reviewId.toString()));
    }

    /**
     * Deletes a review by its ID.
     * @param id The ID of the review to delete.
     * @throws ReviewNotFoundException If the review with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to delete the review.
     */
    public void delete(Integer id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.create(id.toString()));

        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (review.getUser().getUserId().toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this loan.");
        } else {
            reviewRepository.deleteById(id);
        }
    }

    /**
     * Retrieves reviews for a specific book.
     * @param bookId The ID of the book.
     * @return A list of reviews for the specified book.
     */
    public Iterable<ReviewEntity> getReviewsByBook(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    /**
     * Retrieves reviews for a specific user.
     * @param userId The ID of the user.
     * @return A list of reviews for the specified user.
     */
    public Iterable<ReviewEntity> getReviewsByUser(Integer userId) {
        return reviewRepository.findByUserUserId(userId);
    }

    /**
     * Validates the review date.
     * @param reviewDate The review date to validate.
     * @throws InvalidDateException If the review date is in the future.
     */
    private void validateReviewDate(String reviewDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(reviewDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid review date format");
        }
        if (parsedReviewDate.isAfter(currentDate)) {
            throw InvalidDateException.create("Review date cannot be in the future");
        }
    }

    /**
     * Validates the rating.
     * @param rating The rating to validate.
     * @throws RatingOffTheScaleException If the rating is not within the scale of 1 to 10.
     */
    private void validateRating(Integer rating) {
        if (rating < 1 || rating > 10) {
            throw RatingOffTheScaleException.create(rating.toString());
        }
    }

    /**
     * Edits a review.
     * @param reviewId The ID of the review to edit.
     * @param editedReview The edited review entity.
     * @return The edited review entity.
     * @throws ReviewNotFoundException If the review with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to edit the review.
     */
    public ReviewEntity editReview(Integer reviewId, ReviewEntity editedReview) {
        ReviewEntity reviewToEdit = reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewNotFoundException.create(reviewId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        if (!reviewToEdit.getUser().getUserId().toString().equals(loggedInUsername)) {
            throw UserAccessDeniedException.create("You are not allowed to edit this review.");
        }

        Integer newRating = editedReview.getRating();
        String newComment = editedReview.getComment();
        String newReviewDate = editedReview.getReviewDate();

        if (newRating != null) {
            validateRating(newRating);
            reviewToEdit.setRating(newRating);
        }
        if (newComment != null) {
            reviewToEdit.setComment(newComment);
        }
        if (newReviewDate != null) {
            validateReviewDate(newReviewDate);
            reviewToEdit.setReviewDate(newReviewDate);
        }

        return reviewRepository.save(reviewToEdit);
    }
}