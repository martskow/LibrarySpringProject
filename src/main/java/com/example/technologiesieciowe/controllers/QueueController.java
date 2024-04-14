package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.QueueEntity;
import com.example.technologiesieciowe.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for handling queue-related HTTP requests.
 */
@RestController
@RequestMapping("/queue")
public class QueueController {
    private final QueueService queueService;

    /**
     * Constructor injection for QueueController.
     * @param queueService The QueueService instance to be injected.
     */
    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * Endpoint for adding a new queue entry.
     * @param queue The QueueEntity object representing the new queue entry.
     * @return The added QueueEntity object.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public QueueEntity addQueue(@RequestBody QueueEntity queue){
        return queueService.addQueue(queue);
    }

    /**
     * Endpoint for retrieving all queue entries.
     * @return Iterable collection of all QueueEntity objects.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<QueueEntity> getAllLoans(){
        return queueService.getAll();
    }

    /**
     * Endpoint for retrieving a single queue entry by ID.
     * @param id The ID of the queue entry to retrieve.
     * @return The QueueEntity object with the specified ID.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public QueueEntity getOne (@PathVariable Integer id) {
        return queueService.getOne(id);
    }

    /**
     * Endpoint for deleting a queue entry by ID.
     * @param id The ID of the queue entry to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        queueService.delete(id);
    }

    /**
     * Endpoint for ending the waiting queue for a specific entry.
     * @param id The ID of the queue entry to end the waiting for.
     */
    @PutMapping("/endWaiting/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void endQueue(@PathVariable Integer id) {
        queueService.endQueue(id);
    }

    /**
     * Retrieves all queue entries for a specific user.
     * This method is mapped to the endpoint "/getAllUserQueues/{id}" using GET request method.
     *
     * @param id the ID of the user
     */
    @GetMapping("/getAllUserQueues/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<QueueEntity> getAllUserQueues(@PathVariable Integer id) {
        return queueService.getQueueByUser(id);
    }

    /**
     * Retrieves all queue entries for a specific book.
     * This method is mapped to the endpoint "/getAllBookQueues/{id}" using GET request method.
     *
     * @param id the ID of the book
     */
    @GetMapping("/getAllBookQueues/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<QueueEntity> getAllBookQueues(@PathVariable Integer id) {
        return queueService.getQueueByBook(id);
    }
}