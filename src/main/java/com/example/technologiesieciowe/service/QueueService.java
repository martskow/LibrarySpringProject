package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.*;
import com.example.technologiesieciowe.infrastructure.repository.QueueRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.QueueErrors.QueueNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final LoanService loanService;

    @Autowired
    public QueueService(QueueRepository queueRepository, LoanService loanService) {
        this.queueRepository = queueRepository;
        this.loanService = loanService;
    }

    public List<QueueEntity> getAll() {
        return queueRepository.findAll();
    }

    public QueueEntity getOne(Integer queueId) {
        QueueEntity queue = queueRepository.findById(queueId)
                .orElseThrow(() -> QueueNotFoundException.create(queueId.toString()));
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (queue.getUser().getUserId().toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this place in queue.");
        } else {
            return queue;
        }
    }

    public QueueEntity addQueue(QueueEntity queue) {
        if (queue.getQueuingDate() == null) {
            queue.setQueuingDate(LocalDate.now().toString());
        } else if (queue.getBook() == null || queue.getBook().getId() == null) {
            throw FieldRequiredException.create("Book ID");
        } else if (queue.getUser() == null || queue.getUser().getUserId() == null) {
            throw FieldRequiredException.create("User ID");
        }

        return queueRepository.save(queue);
    }

    public void delete (Integer id){
        queueRepository.deleteById(id);
    }

    public void endQueue (Integer id){
        Optional<QueueEntity> queueOptional = queueRepository.findById(id);
        if (queueOptional.isPresent()) {
            QueueEntity queue = queueOptional.get();

            BookEntity book = queue.getBook();
            UserEntity user = queue.getUser();

            LoanEntity loan = new LoanEntity();
            loan.setBook(book);
            loan.setUser(user);
            LocalDate today = LocalDate.now();
            loan.setLoanDate(today.toString());

            loanService.addLoan(loan);
            queueRepository.deleteById(id);
        } else {
            throw QueueNotFoundException.create(id.toString());
        }
    }

    /**
     * Retrieves the queue entries for a specific book.
     *
     * @param bookId the ID of the book
     * @return an iterable collection of queue entries for the book
     */
    public Iterable<QueueEntity> getQueueByBook(Integer bookId) {
        return queueRepository.getByBookId(bookId);
    }

    /**
     * Retrieves the queue entries for a specific user.
     * Access is restricted based on user role.
     *
     * @param userId the ID of the user
     * @throws UserAccessDeniedException if the user does not have permission to access the queue
     */
    public Iterable<QueueEntity> getQueueByUser(Integer userId) {
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (userId.toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this place in queue.");
        } else {
            return queueRepository.getByUserUserId(userId);
        }
    }
}
