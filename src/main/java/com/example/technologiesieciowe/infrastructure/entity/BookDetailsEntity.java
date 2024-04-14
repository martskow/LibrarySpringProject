package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;
/**
 * Entity class representing book details.
 */
@Entity
@Table(name = "book_details")
public class BookDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookdetail_id")
    private Integer bookDetailId;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "cover_imageurl")
    private String coverImageURL;

    /**
     * Get the ID of the book details.
     * @return The ID of the book details.
     */
    public Integer getBookDetailId() {
        return bookDetailId;
    }

    /**
     * Set the ID of the book details.
     * @param bookDetailId The ID of the book details to set.
     */
    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    /**
     * Get the associated book entity.
     * @return The associated book entity.
     */
    public BookEntity getBook() {
        return book;
    }

    /**
     * Set the associated book entity.
     * @param book The book entity to set.
     */
    public void setBook(BookEntity book) {
        this.book = book;
    }

    /**
     * Get the genre of the book.
     * @return The genre of the book.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Set the genre of the book.
     * @param genre The genre of the book to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Get the summary of the book.
     * @return The summary of the book.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set the summary of the book.
     * @param summary The summary of the book to set.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Get the URL of the cover image of the book.
     * @return The URL of the cover image of the book.
     */
    public String getCoverImageURL() {
        return coverImageURL;
    }

    /**
     * Set the URL of the cover image of the book.
     * @param coverImageURL The URL of the cover image of the book to set.
     */
    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }
}