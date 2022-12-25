package com.shop.online.simple.repository.impl;

import com.shop.online.simple.SpringBootContextTestConfiguration;
import com.shop.online.simple.entity.Tag;
import com.shop.online.simple.repository.rowmapper.TagRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = SpringBootContextTestConfiguration.class)
public class TagRepositoryImplIntegrationTest {
    @Autowired
    private TagRepositoryImpl tagRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void whenFindOne_AndTagPresentInDataBase_ThenTrue() {
        Optional<Tag> result = tagRepository.findOne(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndTagsPresentInDataBase_ThenTrue() {
        List<Tag> result = tagRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveNewTag_ThenTagPresentInDataBase() {
        Tag newTag = new Tag("new_tag", "This is some new tag");
        tagRepository.save(newTag);

        List<Tag> result = jdbcTemplate.query("SELECT * FROM tag WHERE name = ?",
                new TagRowMapper(), newTag.getName());

        assertEquals(result.get(0).getName(), newTag.getName());
    }

    @Test
    public void whenUpdateTag_ThenTagUpdatedInDataBase() {
        Tag tagForUpdating = tagRepository.findOne(1L).get();
        tagForUpdating.setName("new_tag_name");

        tagRepository.update(tagForUpdating);
        Tag updatedTag = tagRepository.findOne(1L).get();

        assertEquals(tagForUpdating, updatedTag);
    }

    @Test
    @Transactional
    @Rollback
    public void whenDeleteTag_ThenTagNotPresentInDataBase() {
        Tag tagForDeleting = tagRepository.findOne(1L).get();

        tagRepository.delete(tagForDeleting);
        Optional<Tag> result = tagRepository.findOne(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindByName_AndTagPresentInDataBase_ThenReturnTag() {
        Optional<Tag> result = tagRepository.findByName("tag_name1");

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindByName_AntTagPresentNotInDataBase_ThenReturnOptionalEmpty() {
        Optional<Tag> result = tagRepository.findByName("not_existed_name");

        assertTrue(result.isEmpty());
    }
}
