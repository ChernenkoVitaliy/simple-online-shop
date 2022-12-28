package com.shop.online.simple.repository.rowmapper;

import com.shop.online.simple.entity.Customer;
import com.shop.online.simple.entity.Feedback;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Tag;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("PMD")
public class ProductRowMapper implements ResultSetExtractor<List<Product>> {

    @Override
    public List<Product> extractData(final ResultSet rs) throws SQLException {
        final Map<Long, Product> productByID = new ConcurrentHashMap<>();
        while (rs.next()) {
            final long productId = rs.getLong("id");
            final String productName = rs.getString("name");
            final String productDescription = rs.getString("description");
            final double price = rs.getDouble("price");

            Product product = productByID.get(productId);

            if (product == null) {
                product = new Product(productName, productDescription, price);
                product.setId(productId);
                productByID.put(product.getId(), product);
            }

            populateTag(rs, product);
            populateFeedback(rs, product);
        }

        return new ArrayList<>(productByID.values());
    }

    private void populateTag(final ResultSet rs, final Product product) throws SQLException {
        final long tagID = rs.getLong("tag_id");
        if (tagID > 0) {
            final Tag tag = new Tag();
            tag.setId(tagID);
            tag.setName(rs.getString("tag_name"));
            tag.setDescription(rs.getString("tag_description"));
            product.getTags().add(tag);
        }
    }

    private void populateFeedback(final ResultSet rs, final Product product) throws SQLException {
        final long feedbackId = rs.getLong("feedback_id");
        if(feedbackId > 0) {
            final Feedback feedback = new Feedback();
            feedback.setId(feedbackId);
            feedback.setText(rs.getString("feedback_text"));
            feedback.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            populateAuthor(rs, feedback);

            product.getFeedbacks().add(feedback);
        }
    }

    private void populateAuthor(final ResultSet rs, final Feedback feedback) throws SQLException {
        final Customer author = new Customer();
        author.setId(rs.getLong("customer_id"));
        author.setName(rs.getString("customer_name"));
        author.setSurname(rs.getString("customer_surname"));
        feedback.setAuthor(author);
    }
}
