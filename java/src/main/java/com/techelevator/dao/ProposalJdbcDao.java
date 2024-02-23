package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ProposalDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProposalJdbcDao implements ProposalDao{


    private JdbcTemplate template;

    public ProposalJdbcDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public ProposalDto mapRowToProposal(SqlRowSet rowset) {
        int proposalId = rowset.getInt("proposal_id");
        int campaignId = rowset.getInt("campaign_id");
        String description = rowset.getString("description");
        String proposalName = rowset.getString("proposal_name");
        Boolean votePassed = rowset.getBoolean("vote_passed");
        String proposalStatus = rowset.getString("proposal_status");
        LocalDate proposalDeadline = rowset.getDate("proposal_deadline").toLocalDate();


        ProposalDto proposal = new ProposalDto(proposalId, campaignId, description, votePassed, proposalName, proposalStatus, proposalDeadline);
        return proposal;
    }



    @Override
    public ProposalDto getProposal(int proposalId) {
        String sql = "SELECT * FROM proposal WHERE proposal_id = ?";
        SqlRowSet result = template.queryForRowSet(sql,proposalId);

        ProposalDto proposal = null;
        if (result.next()) {
            proposal = mapRowToProposal(result);
        }
        return proposal;
    }

    @Override
    public ProposalDto createProposal(ProposalDto proposalToCreate, int campaignId) {
        int campaignid = proposalToCreate.getCampaign_id();
        String proposalname = proposalToCreate.getProposal_name();
        String description = proposalToCreate.getDescription();
        String proposal = proposalToCreate.getProposal_status();
        LocalDate proposalDeadline = proposalToCreate.getProposal_deadline();

        String sql = "INSERT INTO proposal (campaign_id, proposal_name, vote_passed, description, proposal_deadline, proposal_status)" +
                " VALUES ((SELECT campaign_id FROM campaign WHERE campaign_id = ?), ?, NULL, ?, ?, ?) RETURNING proposal_id;";
        int newProposalID = template.queryForObject(sql, Integer.class, campaignId, proposalname, description, proposalDeadline, proposal);

        return getProposal(newProposalID);
    }

    @Override
    public void editProposal(ProposalDto proposalToEdit, String username) {
        try {
            if (getUsernameByCampaignId(proposalToEdit.getCampaign_id()).equals(username)) {
                String sql = "UPDATE proposal SET description = ?, proposal_name = ?, " +
                        " proposal_status = ? WHERE proposal_id = ?;";
                template.update(sql,
                        proposalToEdit.getDescription(),
                        proposalToEdit.getProposal_name(),
                        proposalToEdit.getProposal_status(),
                        proposalToEdit.getProposal_id());
            }
        }catch(DataAccessException e){
                throw new DaoException("Cannot edit proposal that isn't yours");
            }
        }


    @Override
    public void deleteProposal(String name,int proposalId,int campaignId) {
        try {
            if (getUsernameByCampaignId(campaignId).equals(name)) {
                String sql = "DELETE FROM proposal WHERE proposal_id = ? AND campaign_id = ?";
                template.update(sql, proposalId, campaignId);
            }else {
                throw new DaoException("Cannot delete proposal that isn't yours");
            }
        } catch (DataAccessException e){
            throw new DaoException("Database error occurred while deleting the proposal", e);
        }

    }

    public String getUsernameByCampaignId(int campaignId){
        String sql = "SELECT username FROM campaign WHERE campaign_id = ?;";
        String username = template.queryForObject(sql, String.class, campaignId);
        return username;
    }

    public List<ProposalDto> getProposalByCampaignId(String username, int campaignId){
        List<ProposalDto> proposalDtoList = new ArrayList<>();

        String sql = "SELECT * from proposal WHERE campaign_id = ?;";
        SqlRowSet result = template.queryForRowSet(sql,campaignId);

        while (result.next()){
            ProposalDto proposalDto = mapRowToProposal(result);
            proposalDtoList.add(proposalDto);
        }

        return proposalDtoList;
    }

}
