package com.techelevator.dao;

import com.techelevator.model.ProposalDto;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ProposalDao {

     ProposalDto getProposal(int proposalId);

     ProposalDto createProposal(ProposalDto proposalToCreate, int campaignId);

     void editProposal(ProposalDto proposalToEdit, String username);

     void deleteProposal(String name,int campaignId, int proposalId);

     List<ProposalDto> getProposalByCampaignId(String username, int campaignId);
     ProposalDto mapRowToProposal(SqlRowSet rowset);


}
