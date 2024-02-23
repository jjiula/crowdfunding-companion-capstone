package com.techelevator.model;

public class DonorUserDto {

    private String username;
    private double amount;
    private String donation_comment;

    public DonorUserDto(String username, double amount, String donation_comment) {
        this.username = username;
        this.amount = amount;
        this.donation_comment = donation_comment;
    }

    public DonorUserDto(String username, double amount){
        this.username = username;
        this.amount = amount;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDonation_comment() {
        return donation_comment;
    }

    public void setDonation_comment(String donation_comment) {
        this.donation_comment = donation_comment;
    }
}
