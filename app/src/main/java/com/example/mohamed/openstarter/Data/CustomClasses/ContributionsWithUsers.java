package com.example.mohamed.openstarter.Data.CustomClasses;

import java.util.Date;

/**
 * Created by Bacem on 12/12/2017.
 */

public class ContributionsWithUsers {

    private Date paymentDate;
    private float amount ;
    private String firstName ;
    private String lastName ;
    private String email ;
    private int id ;

    public ContributionsWithUsers() {
    }

    public ContributionsWithUsers(Date paymentDate, float amount, String firstName, String lastName, String email, int id) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContributionsWithUsers{" +
                "paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
