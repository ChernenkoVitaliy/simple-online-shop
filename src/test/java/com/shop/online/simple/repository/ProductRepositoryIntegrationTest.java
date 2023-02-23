package com.shop.online.simple.repository;

import com.shop.online.simple.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryIntegrationTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void whenFindOne_AndProductPresentInDataBase_ThenTrue() {
        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenFindAll_AndProductsPresentInDataBase_ThenListNotEmpty() {
        List<Product> result = productRepository.findAll();

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenSaveProduct_ThenProductPresentInDataBase() {
        Seller seller = createSeller();
        Product product = new Product("Some new product",
                "Some product description",
                BigDecimal.valueOf(1111.11));
        product.setSeller(seller);

        Product result = productRepository.save(product);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void whenUpdateProduct_ThenProductUpdatedInDataBase() {
        Product productForUpdating = productRepository.findById(1L).get();
        productForUpdating.setName("New Product Name");

        productRepository.save(productForUpdating);
        Product updatedProduct = productRepository.findById(1L).get();

        assertEquals(productForUpdating, updatedProduct);
    }

    @Test
    public void whenDeleteProduct_ThenProductDoNotPresentInDataBase() {
        Product productForDeleting = productRepository.findById(1L).get();

        productRepository.delete(productForDeleting);
        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenGetProductsBySeller_AndSuchProductPresentInDataBase_ThenListReturned() {
        Seller seller = createSeller();
        List<Product> result = productRepository.findAllBySeller(seller);

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenFindProductsByTag_AndSuchProductPresentInDataBase_ThenListReturned() {
        Tag tag = createTag();

        List<Product> result = productRepository.findAllByTag(tag);

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenFindProductsByName_AndSuchProductsPresentInDataBase_ThenListReturned() {
        List<Product> result = productRepository.findAllByNameContainsIgnoreCase("product");

        assertTrue(result.size() > 0);
    }

    private Seller createSeller() {
        return (Seller) entityManager.createQuery("select s FROM Seller s where s.id = 1").getSingleResult();
    }

    private Tag createTag() {
        return (Tag) entityManager.createQuery("select t FROM Tag t where t.id = 1").getSingleResult();
    }
}