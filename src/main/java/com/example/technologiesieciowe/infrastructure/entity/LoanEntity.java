package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Entity class representing a loan.
 */
@Entity
@Table(name = "loans", schema = "library")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private Integer loanId;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "role", "userPassword", "email", "userFirstName", "userLastName"})
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "isbn", "author", "publisher", "publishYear", "availableCopies"})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private String loanDate;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "return_date")
    private String returnDate;

    /**
     * Get the ID of the loan.
     * @return The ID of the loan.
     */
    public Integer getLoanId() {
        return loanId;
    }

    /**
     * Set the ID of the loan.
     * @param loanId The ID of the loan to set.
     */
    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    /**
     * Get the book associated with the loan.
     * @return The book associated with the loan.
     */
    public BookEntity getBook() {
        return book;
    }

    /**
     * Set the book associated with the loan.
     * @param book The book associated with the loan to set.
     */
    public void setBook(BookEntity book) {
        this.book = book;
    }

    /**
     * Get the user associated with the loan.
     * @return The user associated with the loan.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the loan.
     * @param user The user associated with the loan to set.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the loan date of the loan.
     * @return The loan date of the loan.
     */
    public String getLoanDate() {
        return loanDate;
    }

    /**
     * Set the loan date of the loan.
     * @param loanDate The loan date of the loan to set.
     */
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Get the due date of the loan.
     * @return The due date of the loan.
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of the loan.
     * @param dueDate The due date of the loan to set.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the return date of the loan.
     * @return The return date of the loan.
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Set the return date of the loan.
     * @param returnDate The return date of the loan to set.
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}