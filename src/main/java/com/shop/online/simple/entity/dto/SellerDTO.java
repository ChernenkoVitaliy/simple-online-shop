package com.shop.online.simple.entity.dto;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SellerDTO {
    private long id;
    private AccountDTO accountDTO;
    private String companyName;
    private String companyDescription;
    private Set<String> phones;
    private String site;
    private List<ProductDTO> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerDTO sellerDTO = (SellerDTO) o;
        return id == sellerDTO.id && Objects.equals(accountDTO, sellerDTO.accountDTO) && Objects.equals(companyName, sellerDTO.companyName) && Objects.equals(companyDescription, sellerDTO.companyDescription) && Objects.equals(phones, sellerDTO.phones) && Objects.equals(site, sellerDTO.site) && Objects.equals(products, sellerDTO.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountDTO, companyName, companyDescription, phones, site, products);
    }
}
