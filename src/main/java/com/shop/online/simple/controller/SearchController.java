package com.shop.online.simple.controller;

import com.shop.online.simple.entity.Product;
import com.shop.online.simple.entity.dto.ProductDTO;
import com.shop.online.simple.entity.dto.mapper.ProductDTOMapper;
import com.shop.online.simple.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final transient SearchService searchService;
    private final transient ProductDTOMapper productDTOMapper;

    public SearchController(final SearchService searchService, final ProductDTOMapper productDTOMapper) {
        this.searchService = searchService;
        this.productDTOMapper = productDTOMapper;
    }

    @PostMapping
    public String searchResult(@RequestParam("search-name") final String searchText, final Model model) {
        final List<Product> result = searchService.searchProductByName(searchText);

        if (result.isEmpty()) {
            model.addAttribute("notFound", String.format("For your query '%s' not found results.", searchText));

            return "search-result-not-found";
        }

        final List<ProductDTO> products = result.stream().map(productDTOMapper::toProductDTO).collect(Collectors.toList());
        model.addAttribute("products", products);

        return "search-result";
    }
}
