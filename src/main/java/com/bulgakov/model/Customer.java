package com.bulgakov.model;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class Customer extends Person{
    private String invoice;
    private final String role = "Customer";

    public Customer(Integer personId, String name, String invoice, String email, String password) {
        super.personId = personId;
        super.name = name;
        this.invoice = invoice;
        super.email = email;
        super.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        super.name = name;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Customer() {
    }

    public String getRole() {
        return role;
    }
}
