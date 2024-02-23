package com.techelevator.model;

public class DonationDto {

    private int user_id;
    private int campaign_id;

    private double amount;

    private String donation_comment;

    public DonationDto(int user_id, int campaign_id, double amount, String donation_comment) {
        this.user_id = user_id;
        this.campaign_id = campaign_id;
        this.amount = amount;
        this.donation_comment = donation_comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
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
