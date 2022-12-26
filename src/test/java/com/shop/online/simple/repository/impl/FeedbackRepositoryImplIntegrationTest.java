package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class FeedbackRepositoryImplIntegrationTest {
    @Autowired
    private FeedbackRepositoryImpl feedbackRepository;

    @Test
    public void whenFindOne_AndFeedbackPresentInDataBase_ThenTrue() {
        Optional<Feedback> result = feedbackRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndFeedbacksPresentInDataBase_ThenTrue() {
        List<Feedback> result = feedbackRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteFeedback_ThenFeedbackNotPresentInDataBase() {
        Feedback feedbackForDeleting = feedbackRepository.findOne(1L).get();

        feedbackRepository.delete(feedbackForDeleting);
        Optional<Feedback> result = feedbackRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }
}
