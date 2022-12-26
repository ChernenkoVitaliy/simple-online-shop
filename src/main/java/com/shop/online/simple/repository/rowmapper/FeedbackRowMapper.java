package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackRowMapper implements RowMapper<Feedback> {

    @Override
    public Feedback mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Feedback feedback = new Feedback();
        final Customer author = new Customer();
        feedback.setId(rs.getLong("id"));
        feedback.setText(rs.getString("feedback_text"));
        feedback.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        author.setName(rs.getString("name"));
        author.setSurname(rs.getString("surname"));
        feedback.setAuthor(author);

        return feedback;
    }
}
