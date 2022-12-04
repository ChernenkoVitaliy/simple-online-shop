package com.shop.online.simple.service.impl;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.repository.ProductRepository;
import com.shop.online.simple.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SellerServiceImplTest {
    private SellerService sellerService;
    private ProductRepository productRepository = mock(ProductRepository.class);
    private Product newProduct = createProduct();
    private Product productWithId = createProduct();
    private Seller seller = createSeller();

    @BeforeEach
    public void setUp() {
        sellerService = new SellerServiceImpl(productRepository);
        productWithId.setId(1L);
        seller.getProducts().add(productWithId);
    }

    @Test
    public void whenAddNewProduct_thenReturnProductWithId() {
        when(productRepository.save(newProduct)).thenReturn(productWithId);

        var addedProduct = sellerService.addNewProduct(newProduct, seller);

        assertTrue(addedProduct.getId() > 0);
    }

    @Test
    public void addNewProductMethod_ShouldThrowException_WhenProductIsNull() {
        assertThatThrownBy(() -> sellerService.addNewProduct(null, seller))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addNewProductMethod_ShouldThrowException_WhenSellerIsNull() {
        assertThatThrownBy(() -> sellerService.addNewProduct(newProduct, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenSellerDeleteOwnProduct_AndProductExists_ThenReturnTrue() {
        when(productRepository.delete(productWithId)).thenReturn(true);

        var result = sellerService.deleteProduct(productWithId, seller);

        assertTrue(result);
    }

    @Test
    public void whenSellerTryToDeleteNotOwnProduct_ThenReturnFalse() {
        var anotherProduct = createProduct();
        anotherProduct.setId(666L);

        var result = sellerService.deleteProduct(anotherProduct, seller);

        assertFalse(result);
    }

    private Product createProduct() {
        return new Product("Product name",
                "Some product description",
                5000L);
    }

    private Seller createSeller() {
        Account account = new Account("some_email@some.com", "password");
        Set<String> phones = new HashSet<>();
        phones.add("012-34-56-78");

        return new Seller(account,
                "Company name",
                "Company description",
                phones,
                "site.com");
    }
}
