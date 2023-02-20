package com.shop.online.simple.entity.dto.mapper;

import com.shop.online.simple.entity.Seller;
import com.shop.online.simple.entity.dto.ProductDTO;
import com.shop.online.simple.entity.dto.SellerDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SellerDTOMapper {
    private final transient AccountDTOMapper accountDTOMapper;
    private final transient ProductDTOMapper productDTOMapper;

    public SellerDTOMapper(final AccountDTOMapper accountDTOMapper, final ProductDTOMapper productDTOMapper) {
        this.accountDTOMapper = accountDTOMapper;
        this.productDTOMapper = productDTOMapper;
    }

    public SellerDTO toSellerDTO(final Seller seller) {
        final SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(seller.getId());
        sellerDTO.setAccountDTO(accountDTOMapper.toAccountDTO(seller.getAccount()));
        sellerDTO.setCompanyName(seller.getCompanyName());
        sellerDTO.setCompanyDescription(seller.getCompanyDescription());
        sellerDTO.setSite(seller.getSite());
        sellerDTO.setPhones(Collections.unmodifiableSet(seller.getPhones()));
        List<ProductDTO> listDTOProducts = seller.getProducts().stream().map(productDTOMapper::toProductDTO).collect(Collectors.toList());
        sellerDTO.setProducts(listDTOProducts);

        return sellerDTO;
    }

}
