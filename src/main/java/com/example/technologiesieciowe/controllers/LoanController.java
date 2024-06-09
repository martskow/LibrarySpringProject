package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling loan-related HTTP requests.
 */
@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    /**
     * Constructor injection for LoanController.
     * @param loanService The LoanService instance to be injected.
     */
    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    /**
     * Endpoint for adding a new loan.
     * @param loan The LoanEntity object representing the new loan.
     * @return The added LoanEntity object.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public LoanEntity addLoan(@RequestBody LoanEntity loan){
        return loanService.addLoan(loan);
    }

    /**
     * Endpoint for retrieving all loans.
     * @return Iterable collection of all LoanEntity objects.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<LoanEntity> getAllLoans(){
        return loanService.getAll();
    }

    /**
     * Endpoint for retrieving a single loan by ID.
     * @param id The ID of the loan to retrieve.
     * @return The LoanEntity object with the specified ID.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LoanEntity getOne (@PathVariable Integer id) {
        return loanService.getOne(id);
    }

    /**
     * Endpoint for deleting a loan by ID.
     * @param id The ID of the loan to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        loanService.delete(id);
    }

    /**
     * Endpoint for extending the due date of a loan.
     * @param id The ID of the loan to extend.
     * @param editedLoan The LoanEntity object containing the updated due date.
     * @return The LoanEntity object with the extended due date.
     */
    @PutMapping("/extendDueDate/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LoanEntity extendDueDate(@PathVariable Integer id, @RequestBody LoanEntity editedLoan) {
        return loanService.extendDueDateLoan(id, editedLoan);
    }

    /**
     * Endpoint for returning a book associated with a loan.
     * @param id The ID of the loan to return.
     */
    @PutMapping("/returnBook/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void returnLoan(@PathVariable Integer id) {
        loanService.returnBook(id);
    }

    /**
     * Retrieves all queue entries for a specific user.
     * This method is mapped to the endpoint "/getAllUserQueues/{id}" using GET request method.
     *
     * @param id the ID of the user
     */
    @GetMapping("/getAllUserLoans/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<LoanEntity> getAllUserLoans(@PathVariable Integer id) {
        return loanService.getLoanByUser(id);
    }

    /**
     * Retrieves all queue entries for a specific book.
     * This method is mapped to the endpoint "/getAllBookQueues/{id}" using GET request method.
     *
     * @param id the ID of the book
     */
    @GetMapping("/getAllBookLoans/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<LoanEntity> getAllBookLoans(@PathVariable Integer id) {
        return loanService.getLoanByBook(id);
    }

    /**
     * Retrieves all delayed loans where the due date is before today's date.
     *
     * @return An iterable collection of LoanEntity objects representing delayed loans.
     */
    @GetMapping("/getAllDelays")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<LoanEntity> getDelays() {
        return loanService.getDelays();
    }

    /**
     * Retrieves all delayed loans for a specific user, where the due date is before today's date.
     *
     * @param id The ID of the user for whom to retrieve delayed loans.
     * @return An iterable collection of LoanEntity objects representing delayed loans for the specified user.
     */
    @GetMapping("/getUserDelays/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<LoanEntity> getUserDelays(@PathVariable Integer id) {
        return loanService.getUserDelays(id);
    }
}
