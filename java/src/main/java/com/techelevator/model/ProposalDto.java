package com.techelevator.model;

import java.time.LocalDate;
import java.util.Date;

public class ProposalDto {

    private int proposal_id;

    private int campaign_id;

    private String proposal_name;

    private boolean vote_passed;
    private String description;

    private String proposal_status;

    private LocalDate proposal_deadline;


    public ProposalDto(int proposal_id, int campaign_id, String proposal_name, boolean vote_passed, String description, String proposal_status, LocalDate proposal_deadline) {
        this.proposal_id = proposal_id;
        this.campaign_id = campaign_id;
        this.proposal_name = proposal_name;
        this.vote_passed = vote_passed;
        this.description = description;
        this.proposal_status = proposal_status;
        this.proposal_deadline = proposal_deadline;
    }

    public int getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(int proposal_id) {
        this.proposal_id = proposal_id;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProposal_name() {
        return proposal_name;
    }

    public void setProposal_name(String proposal_name) {
        this.proposal_name = proposal_name;
    }

    public boolean isVote_passed() {
        return vote_passed;
    }

    public void setVote_passed(boolean vote_passed) {
        this.vote_passed = vote_passed;
    }

    public String getProposal_status() {
        return proposal_status;
    }

    public void setProposal_status(String proposal_status) {
        this.proposal_status = proposal_status;
    }

    public LocalDate getProposal_deadline() {
        return proposal_deadline;
    }

    public void setProposal_deadline(LocalDate proposal_deadline) {
        this.proposal_deadline = proposal_deadline;
    }
}
