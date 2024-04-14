package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanArchiveEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.service.LoanArchiveService;
import com.example.technologiesieciowe.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for handling loan archive-related HTTP requests.
 */
@RestController
@RequestMapping("/loanArchive")
public class LoanArchiveController {
    private final LoanArchiveService loanArchiveService;

    /**
     * Constructor injection for LoanArchiveController.
     * @param loanArchiveService The LoanArchiveService instance to be injected.
     */
    @Autowired
    public LoanArchiveController(LoanArchiveService loanArchiveService){
        this.loanArchiveService = loanArchiveService;
    }

    /**
     * Endpoint to retrieve all loan archive entries.
     * @return Iterable of LoanArchiveEntity representing all loan archive entries.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<LoanArchiveEntity> getAllLoansArchive(){
        return loanArchiveService.getAll();
    }

    /**
     * Endpoint to retrieve a single loan archive entry by its ID.
     * @param id The ID of the loan archive entry to retrieve.
     * @return LoanArchiveEntity representing the requested loan archive entry.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LoanArchiveEntity getOne (@PathVariable Integer id) {
        return loanArchiveService.getOne(id);
    }

    /**
     * Endpoint to delete a loan archive entry by its ID.
     * @param id The ID of the loan archive entry to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        loanArchiveService.delete(id);
    }
}
