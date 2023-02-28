package com.shop.online.simple.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shop.online.simple.entity.Account;
import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.dto.AccountDTO;
import com.shop.online.simple.entity.dto.ProductCreationDTO;
import com.shop.online.simple.entity.dto.ProductDTO;
import com.shop.online.simple.entity.dto.SellerDTO;
import com.shop.online.simple.entity.dto.mapper.ProductDTOMapper;
import com.shop.online.simple.entity.dto.mapper.SellerDTOMapper;
import com.shop.online.simple.entity.enums.AccountStatus;
import com.shop.online.simple.repository.SellerRepository;
import com.shop.online.simple.service.SellerService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;
    @MockBean
    private SellerRepository sellerRepository;
    @MockBean
    private ProductDTOMapper productDTOMapper;
    @MockBean
    private SellerDTOMapper sellerDTOMapper;

    @Test
    public void showFormAddProduct_ShouldReturnWebFormToAddingNewProduct() throws Exception {
        mockMvc.perform(get("/product/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Please fill form below")))
                .andExpect(content().string(containsString("Name:")))
                .andExpect(content().string(containsString("Description:")))
                .andExpect(content().string(containsString("Price:")));
    }

    @Test
    public void whenFormSent_thenShouldBeStatusOkAndContentPresent() throws Exception {
        Product product = createProduct();
        Seller seller = createSeller();
        SellerDTO sellerDTO = createSellerDTO(seller);
        ProductDTO productDTO = createProductDTO(product);
        when(productDTOMapper.toProduct(any(ProductCreationDTO.class))).thenReturn(product);
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(sellerService.addNewProduct(any(Product.class), any(Seller.class))).thenReturn(product);
        when(productDTOMapper.toProductDTO(product)).thenReturn(productDTO);
        when(sellerDTOMapper.toSellerDTO(any(Seller.class))).thenReturn(sellerDTO);
        mockMvc.perform(post("/product/add")
                .param("name", "Some product name")
                .param("description", "Some product description")
                .param("price", String.valueOf(10000))
                .param("sellerId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Overview")))
                .andExpect(content().string(containsString(product.getName())));
    }

    @Test
    public void whenFormSent_AndWrongFillInForm_ThenErrorMessagePresent() throws Exception {
        mockMvc.perform(post("/product/add")
                        .param("name", "")
                        .param("description", "")
                        .param("price", String.valueOf(10000))
                        .param("sellerId", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("nameError"))
                .andExpect(model().attributeExists("descriptionError"));
    }

    private Account createAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setEmail("email@mail.com");
        account.setPassword("paSSword");
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);

        return account;
    }

    private Seller createSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setAccount(createAccount());
        seller.setCompanyName("CompanyName");
        seller.setCompanyDescription("Some company description");
        seller.setPhones(new HashSet<>(List.of("111-11-11")));
        seller.setSite("company.com");

        return seller;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(100L);
        product.setName("Some product name");
        product.setDescription("Some product description");
        product.setPrice(BigDecimal.valueOf(10000L));

        return product;
    }

    private ProductDTO createProductDTO(Product product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setTags(product.getTags());
        productDTO.setFeedbacks(product.getFeedbacks());

        return productDTO;
    }

    public SellerDTO createSellerDTO(Seller seller) {
        final SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(seller.getId());
        sellerDTO.setAccountDTO(createAccountDTO(seller.getAccount()));
        sellerDTO.setCompanyName(seller.getCompanyName());
        sellerDTO.setCompanyDescription(seller.getCompanyDescription());
        sellerDTO.setSite(seller.getSite());
        sellerDTO.setPhones(Collections.unmodifiableSet(seller.getPhones()));

        return sellerDTO;
    }

    public AccountDTO createAccountDTO(Account account) {
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setAccountStatus(account.getAccountStatus());

        return accountDTO;
    }
}
