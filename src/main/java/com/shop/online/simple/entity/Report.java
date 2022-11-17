package com.shop.online.simple.entity;

import java.util.Objects;

public class Report {
    private Customer customer;
    private Order order;

    public Report(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return customer.equals(report.customer) && order.equals(report.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, order);
    }
}
