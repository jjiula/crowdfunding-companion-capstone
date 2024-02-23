package com.techelevator.model;

public class VoteDto {

    private int user_id;
    private int vote_id;
    private int proposal_id;
    private boolean vote_response;

    public VoteDto(int user_id,int vote_id, int proposal_id, boolean vote_response) {
        this.user_id = user_id;
        this.vote_id = vote_id;
        this.proposal_id = proposal_id;
        this.vote_response = vote_response;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public int getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(int proposal_id) {
        this.proposal_id = proposal_id;
    }

    public boolean isVote_response() {
        return vote_response;
    }

    public void setVote_response(boolean vote_response) {
        this.vote_response = vote_response;
    }
}


