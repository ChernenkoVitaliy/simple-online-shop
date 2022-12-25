package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Tag tag = new Tag();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        tag.setDescription(rs.getString("description"));

        return tag;
    }
}
