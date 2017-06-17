package com.bulgakov.model;

import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class Project {
    private Integer projectId;
    private String name;
    private Calendar dateBegin;
    private Calendar dateEnd;
    private Double price;
    private String description;
    private Customer customer;

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setDateBegin(Calendar dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Project(Integer projectId, String name, Customer customer) {
        this.projectId = projectId;
        this.name = name;
        this.customer = customer;
        this.dateBegin = Calendar.getInstance();
    }

    public Project(Integer projectId, String name, Double price, String description, Customer customer) {
        this.projectId = projectId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.customer = customer;
        this.dateBegin = Calendar.getInstance();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public Calendar getDateBegin() {
        return dateBegin;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Project() {}
}
