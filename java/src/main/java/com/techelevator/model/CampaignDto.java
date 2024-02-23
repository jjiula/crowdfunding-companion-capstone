package com.techelevator.model;

public class CampaignDto {

    private int campaign_id;
    private String username;
    private String campaignName;
    private String campaignImage;
    private String campaignType;
    private String description;
    private double amountGoal;
    private double balance;


    public CampaignDto(){}


    public CampaignDto(int campaign_id, String username, String campaignName, String campaignImage, String campaignType, String description, double amountGoal, double balance) {
        this.campaign_id = campaign_id;
        this.username = username;
        this.campaignName = campaignName;
        this.campaignImage = campaignImage;
        this.campaignType = campaignType;
        this.description = description;
        this.amountGoal = amountGoal;
        this.balance = balance;
    }

    public String getCampaignImage() {
        return campaignImage;
    }

    public void setCampaignImage(String campaignImage) {
        this.campaignImage = campaignImage;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmountGoal() {
        return amountGoal;
    }

    public void setAmountGoal(double amountGoal) {
        this.amountGoal = amountGoal;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
