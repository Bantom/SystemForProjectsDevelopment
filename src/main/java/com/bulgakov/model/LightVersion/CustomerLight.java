package com.bulgakov.model.LightVersion;

import com.bulgakov.model.Customer;

import java.io.Serializable;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class CustomerLight implements Serializable{
    public Integer personId;
    public String email;
    public String password;
    public String name;
    public String invoice;

    public CustomerLight(Customer customer) {
        this.personId = customer.getPersonId();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.name = customer.getName();
        this.invoice = customer.getInvoice();
    }

    public CustomerLight() {
    }
}
