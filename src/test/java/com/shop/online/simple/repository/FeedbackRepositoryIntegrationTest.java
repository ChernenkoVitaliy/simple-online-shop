package com.shop.online.simple.repository;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class FeedbackRepositoryIntegrationTest {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void whenFindOne_AndFeedbackPresentInDataBase_ThenTrue() {
        Optional<Feedback> result = feedbackRepository.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndFeedbacksPresentInDataBase_ThenTrue() {
        List<Feedback> result = feedbackRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewFeedback_ThenFeedbackPresentInDataBase() {
        Product product = createProduct();
        Customer author = createAuthor();
        Feedback newFeedback = new Feedback("Some feedback text", LocalDateTime.now());
        newFeedback.setProduct(product);
        newFeedback.setAuthor(author);

        Feedback result = feedbackRepository.save(newFeedback);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenDeleteFeedback_ThenFeedbackNotPresentInDataBase() {
        Feedback feedbackForDeleting = feedbackRepository.findById(1L).get();

        feedbackRepository.delete(feedbackForDeleting);
        Optional<Feedback> result = feedbackRepository.findById(1L);

        assertTrue(result.isEmpty());
    }


    private Customer createAuthor() {
        return (Customer) entityManager.createQuery("SELECT c FROM Customer c WHERE c.id = 1").getSingleResult();
    }

    private Product createProduct() {
        return (Product) entityManager.createQuery("SELECT p FROM Product p WHERE p.id = 1").getSingleResult();
    }
}