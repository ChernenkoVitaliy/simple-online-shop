package com.shop.online.simple.entity.dto.mapper;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.dto.ProductCreationDTO;
import com.shop.online.simple.entity.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {

    public Product toProduct(final ProductCreationDTO productCreationDTO) {
        final Product product = new Product();
        product.setName(productCreationDTO.getName());
        product.setDescription(productCreationDTO.getDescription());
        product.setPrice(productCreationDTO.getPrice());

        return product;
    }

    public ProductDTO toProductDTO(final Product product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setTags(product.getTags());
        productDTO.setFeedbacks(product.getFeedbacks());

        return productDTO;
    }
}
