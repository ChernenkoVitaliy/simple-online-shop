package com.shop.online.simple.repository.impl;

import com.shop.online.simple.entity.Tag;
import com.shop.online.simple.repository.TagRepository;
import com.shop.online.simple.repository.rowmapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_ONE_SQL = "SELECT * FROM tag WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM tag;";
    private static final String INSERT_SQL = "INSERT INTO tag(name, description) VALUES(?, ?);";
    private static final String UPDATE_SQL = "UPDATE tag SET name = ?, description = ? WHERE id = ?;";
    private static final String DELETE_ONE_SQL = "DELETE FROM tag WHERE id = ?";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM tag WHERE name = ?;";

    private transient final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tag> findOne(final long id) {
        final List<Tag> result = jdbcTemplate.query(SELECT_ONE_SQL, new TagRowMapper(), id);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new TagRowMapper());
    }

    @Override
    public void save(final Tag tag) {
        jdbcTemplate.update(INSERT_SQL, tag.getName(), tag. getDescription());
    }

    @Override
    public void update(final Tag tag) {
        jdbcTemplate.update(UPDATE_SQL, tag.getName(), tag.getDescription(), tag.getId());
    }

    @Override
    public void delete(final Tag tag) {
        jdbcTemplate.update(DELETE_ONE_SQL, tag.getId());
    }

    @Override
    public Optional<Tag> findByName(final String name) {
        final List<Tag> result = jdbcTemplate.query(FIND_BY_NAME_SQL, new TagRowMapper(), name);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
