package com.shop.online.simple.entity;

import java.util.*;

public class Seller {
    private Account account;
    private String companyName;
    private String companyDescription;
    private Set<String> phones;
    private String site;
    private List<Product> products;

    public Seller(Account account, String companyName, String companyDescription, Set<String> phones, String site) {
        this.account = account;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.phones = phones;
        this.site = site;
        this.products = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return account.equals(seller.account) && companyName.equals(seller.companyName) && companyDescription.equals(seller.companyDescription) && phones.equals(seller.phones) && Objects.equals(site, seller.site) && Objects.equals(products, seller.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, companyName, companyDescription, phones, site, products);
    }
}
