package com.shop.online.simple.controller;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.dto.ProductCreationDTO;
import com.shop.online.simple.entity.dto.ProductDTO;
import com.shop.online.simple.entity.dto.SellerDTO;
import com.shop.online.simple.entity.dto.mapper.ProductDTOMapper;
import com.shop.online.simple.entity.dto.mapper.SellerDTOMapper;
import com.shop.online.simple.repository.SellerRepository;
import com.shop.online.simple.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final transient SellerService sellerService;
    private final transient SellerRepository sellerRepository;
    private final transient ProductDTOMapper productDTOMapper;
    private final transient SellerDTOMapper sellerDTOMapper;

    public ProductController(final SellerService sellerService, final SellerRepository sellerRepository,
                             final ProductDTOMapper productDTOMapper, final SellerDTOMapper sellerDTOMapper) {
        this.sellerService = sellerService;
        this.sellerRepository = sellerRepository;
        this.productDTOMapper = productDTOMapper;
        this.sellerDTOMapper = sellerDTOMapper;
    }

    @GetMapping("/add")
    public String showFormAddProduct(final Model model) {
        model.addAttribute("product", new ProductCreationDTO());
        return "add-new-product";
    }

    @PostMapping("/add")
    public String handleNewProduct(@Valid final ProductCreationDTO productCreationDTO, final BindingResult bindingResult, final Model model) {

        if (bindingResult.hasErrors()){
            populateErrors("name", bindingResult, model);
            populateErrors("description", bindingResult, model);
            populateErrors("price", bindingResult, model);
            model.addAttribute("product", productCreationDTO);

            return "add-new-product";
        }

        final Product product = productDTOMapper.toProduct(productCreationDTO);
        final Seller seller = sellerRepository.findById(productCreationDTO.getSellerId()).get();
        final Product addedProduct = sellerService.addNewProduct(product, seller);
        final ProductDTO productDTO = productDTOMapper.toProductDTO(addedProduct);
        final SellerDTO sellerDTO = sellerDTOMapper.toSellerDTO(seller);

        model.addAttribute("product", productDTO);
        model.addAttribute("seller", sellerDTO);

        return "product-added";
    }

    private void populateErrors(final String field, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasFieldErrors(field)) {
            model.addAttribute(field + "Error", bindingResult.getFieldError(field).getDefaultMessage());
        }
    }
}
