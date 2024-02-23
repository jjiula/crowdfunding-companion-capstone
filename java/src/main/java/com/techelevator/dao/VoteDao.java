package com.techelevator.dao;

import com.techelevator.model.VoteDto;

import java.time.LocalDate;
import java.util.List;

public interface VoteDao {

     void addVote(VoteDto vote, String username, int campaign_id);
     double totalVotesByProposalId(int proposalId);
     double totalTrueVotesByProposalId(int proposalId);
     void updateProposalStatus(int proposalId);
     double getCampaignBalanceByProposalId(int proposal_id);
     double getCampaignGoalByProposalId(int proposal_id);
     LocalDate getProposalDeadlineByProposalId(int proposal_id);
     double calculateVote(int proposalId);
}
