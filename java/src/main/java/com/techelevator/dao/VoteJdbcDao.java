package com.techelevator.dao;

import com.techelevator.model.VoteDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.time.LocalDate;


@Component
public class VoteJdbcDao implements VoteDao {
    private JdbcTemplate template;

    public VoteJdbcDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override

    public void addVote(VoteDto vote, String username, int campaign_id) {
        LocalDate proposalDeadline = getProposalDeadlineByProposalId(vote.getProposal_id());
        boolean hasDonated = hasUserDonatedToCampaign(getUserIdByUsername(username), campaign_id);

        if(hasDonated) {
            // can balance >= goal ? what if the last person donated more than the goal?
            if (getCampaignBalanceByProposalId(vote.getProposal_id()) == getCampaignGoalByProposalId(vote.getProposal_id())
                    && LocalDate.now().isBefore(proposalDeadline)) {

                boolean hasVoted = hasUserVotedForProposal(getUserIdByUsername(username), vote.getProposal_id());


                if (hasVoted) {
                    String sql = "UPDATE vote SET vote_response = ? WHERE user_id = ? AND proposal_id = ?";
                    template.update(sql, vote.isVote_response(), getUserIdByUsername(username), vote.getProposal_id());
                } else {
                    String sql = "INSERT INTO vote(user_id ,proposal_id,vote_response) VALUES (?, ?, ?)";
                    template.update(sql, getUserIdByUsername(username), vote.getProposal_id(), vote.isVote_response());
                }
            }
        }
    }

    public int getUserIdByUsername(String username){
        String sql = "SELECT user_id from users where username = ?;";
        return template.queryForObject(sql, Integer.class,username);
    }



    public boolean hasUserVotedForProposal(int userId, int proposalId) {
        String sql = "SELECT COUNT(*) FROM vote WHERE user_id = ? AND proposal_id = ?";
        int count = template.queryForObject(sql, Integer.class, userId, proposalId);
        return count > 0;
    }

    public boolean hasUserDonatedToCampaign(int userId, int campaignId){
        String sql = "SELECT COUNT(*) FROM donation WHERE user_id = ? AND campaign_id = ?;";
        int count = template.queryForObject(sql, Integer.class, userId, campaignId);
        return count > 0;
    }






    public double getCampaignBalanceByProposalId(int proposal_id){

            String sql = "select campaign.balance\n" +
                    "from campaign\n" +
                    "join proposal on campaign.campaign_id = proposal.campaign_id\n" +
                    "where proposal.proposal_id = ?;";

            return template.queryForObject(sql, Double.class ,proposal_id);
    }

    public double getCampaignGoalByProposalId(int proposal_id){
        String sql = "select campaign.amountGoal\n" +
                "from campaign\n" +
                "join proposal on campaign.campaign_id = proposal.campaign_id\n" +
                "where proposal.proposal_id = ?;";

        return template.queryForObject(sql, Double.class ,proposal_id);
    }

    public LocalDate getProposalDeadlineByProposalId(int proposal_id){
        String sql = "select proposal_deadline from proposal where proposal_id = ?;";

        return template.queryForObject(sql, LocalDate.class ,proposal_id);
    }




    public void updateProposalStatus(int proposalId) {

        if (calculateVote(proposalId) > .50){

            String sql = "UPDATE proposal SET vote_passed = true, proposal_status = 'Archived' WHERE proposal_id = ?;";

            template.update(sql, proposalId);

        } else {
            String sql2 = "UPDATE proposal SET vote_passed = false, proposal_status = 'Archived' WHERE proposal_id = ?;";

            template.update(sql2, proposalId);
        }
    }



    public double calculateVote(int proposalId){
        return (totalTrueVotesByProposalId(proposalId) / totalVotesByProposalId(proposalId));
    }



    @Override
    public double totalTrueVotesByProposalId(int proposalId) {
        String sql2 = "SELECT COUNT(*) AS total_true_votes\n" +
                "FROM vote\n" +
                "WHERE vote_response = TRUE AND proposal_id = ?;";
        double trueTally = template.queryForObject(sql2, Double.class ,proposalId);
        return trueTally;

    }

    @Override
    public double totalVotesByProposalId(int proposalId) {
        String sql3 = "SELECT COUNT(*) AS total_votes\n" +
                "FROM vote\n" +
                "WHERE proposal_id = ?;";
        double totalTally = template.queryForObject(sql3, Double.class,proposalId);
        return totalTally;
    }


}
