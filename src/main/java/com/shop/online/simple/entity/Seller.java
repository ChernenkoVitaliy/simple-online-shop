package com.shop.online.simple.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_generator")
    @SequenceGenerator(name = "seller_generator", sequenceName = "seller_id_seq", allocationSize = 1)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_description", nullable = false)
    private String companyDescription;

    @Column(name = "company_site")
    private String site;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "seller_phones")
    @Column(name = "phone", unique = true, nullable = false)
    private Set<String> phones;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Seller() {}

    public Seller(Account account, String companyName, String companyDescription, String site, Set<String> phones) {
        this.account = account;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.site = site;
        this.phones = phones;
        this.products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == seller.id && account.equals(seller.account) && companyName.equals(seller.companyName) && companyDescription.equals(seller.companyDescription) && site.equals(seller.site) && phones.equals(seller.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, companyName, companyDescription, site, phones);
    }
}
