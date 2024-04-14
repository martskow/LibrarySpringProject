package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
/**
 * Entity class representing a review.
 */
@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "isbn", "author", "publisher", "publishYear", "availableCopies"})
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "role", "userPassword", "email", "userFirstName", "userLastName"})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Basic
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Basic
    @Column(name = "comment")
    private String comment;

    @Basic
    @Column(name = "review_date", nullable = false)
    private String reviewDate;

    /**
     * Get the ID of the review.
     * @return The ID of the review.
     */
    public Integer getReviewId() {
        return reviewId;
    }

    /**
     * Set the ID of the review.
     * @param reviewId The ID of the review to set.
     */
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Get the book associated with the review.
     * @return The book associated with the review.
     */
    public BookEntity getBook() {
        return book;
    }

    /**
     * Set the book associated with the review.
     * @param book The book associated with the review to set.
     */
    public void setBook(BookEntity book) {
        this.book = book;
    }

    /**
     * Get the user associated with the review.
     * @return The user associated with the review.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the review.
     * @param user The user associated with the review to set.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the rating of the review.
     * @return The rating of the review.
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Set the rating of the review.
     * @param rating The rating of the review to set.
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Get the comment of the review.
     * @return The comment of the review.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment of the review.
     * @param comment The comment of the review to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the review date of the review.
     * @return The review date of the review.
     */
    public String getReviewDate() {
        return reviewDate;
    }

    /**
     * Set the review date of the review.
     * @param reviewDate The review date of the review to set.
     */
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}