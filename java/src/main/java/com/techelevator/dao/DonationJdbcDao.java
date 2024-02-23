package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.DonationDto;
import com.techelevator.model.DonorUserDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DonationJdbcDao implements DonationDao{

    private final JdbcTemplate template;

    public DonationJdbcDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public String createDonation(DonationDto donationToCreate) {
        int userId = donationToCreate.getUser_id();
        int campaignId = donationToCreate.getCampaign_id();
        double amount = donationToCreate.getAmount();
        String donationComment = donationToCreate.getDonation_comment();



        if (getBalanceByCampaignId(campaignId) == getAmountGoalByCampaignId(campaignId)){

            return "Funding goal already met";
        } else if (getAmountDifferenceByCampaignId(campaignId,amount) > getAmountGoalByCampaignId(campaignId)) {
            return "Maximum donation allowed $" +  (getAmountGoalByCampaignId(campaignId) - getBalanceByCampaignId(campaignId));
        }
            String sql = "INSERT INTO donation(user_id, campaign_id, amount, donation_comment) VALUES (?, ?, ?, ?);";
            template.update(sql,userId, campaignId, amount, donationComment);
            updateBalanceForCampaign(campaignId, amount);
            return "Thank you for donating!";

    }


    public double getBalanceByCampaignId(int campaign_id){

        String sql = "SELECT balance FROM campaign WHERE campaign_id = ?";
        return template.queryForObject(sql, Double.class,campaign_id);
    }

    public double getAmountGoalByCampaignId(int campaign_id){
        String sql = "SELECT amountGoal FROM campaign WHERE campaign_id = ?";
        return template.queryForObject(sql, Double.class,campaign_id);
    }

    public double getAmountDifferenceByCampaignId(int campaign_id, double amount){
        String sql = "SELECT balance FROM campaign WHERE campaign_id = ?";
        double balance = template.queryForObject(sql, Double.class,campaign_id);

        return balance + amount;
    }



    public void updateBalanceForCampaign(int campaignId, double amount){
        String sql = "SELECT balance FROM campaign WHERE campaign_id = ?";
        double balance = template.queryForObject(sql, Double.class,campaignId);

        double newBalance = balance + amount;

        String sql2 = "UPDATE campaign SET balance = ? WHERE campaign_id = ?";
        template.update(sql2,
                newBalance,
                campaignId
                );
    }


    public DonorUserDto mapRowToDonorUser(SqlRowSet rowset) {
        String username = rowset.getString("username");

        double amount = rowset.getDouble("amount");

        String donation_comment = rowset.getString("donation_comment");

        DonorUserDto donation = new DonorUserDto(username, amount, donation_comment);
        return donation;
    }

    public String getCreatorByCampaignId(int campaignId){
        String sql = "SELECT username FROM campaign WHERE campaign_id = ?;";

        String username = template.queryForObject(sql, String.class, campaignId);

        return username;
    }


    @Override
    public List<DonorUserDto> getDonationsByCampaignIdForCreator(int campaign_id, String username) {
        List<DonorUserDto> donationUserDtoList = new ArrayList<>();

        try {
            if (getCreatorByCampaignId(campaign_id).equals(username)){
                String sql = "SELECT donation.amount, users.username\n" +
                        "FROM donation\n" +
                        "JOIN users ON donation.user_id = users.user_id\n" +
                        "WHERE donation.campaign_id = ?;\n";


                SqlRowSet results = template.queryForRowSet(sql, campaign_id);

                while (results.next()) {
                    donationUserDtoList.add(mapRowToDonorUser(results));
                }

            }
        }catch (DataAccessException e){
            throw new DaoException("Cannot view donations of campaign that's not yours");
        }
        return donationUserDtoList;
    }

    @Override
    public List<DonorUserDto> getDonationsByCampaignId(int campaign_id) {
        List<DonorUserDto> donationUserDtoList = new ArrayList<>();

        try {
                String sql = "SELECT donation.amount, users.username, donation.donation_comment\n" +
                        "FROM donation\n" +
                        "JOIN users ON donation.user_id = users.user_id\n" +
                        "WHERE donation.campaign_id = ?;\n";


                SqlRowSet results = template.queryForRowSet(sql, campaign_id);

                while (results.next()) {
                    donationUserDtoList.add(mapRowToDonorUser(results));
                }
        }catch (DataAccessException e){
            throw new DaoException("Cannot view donations of campaign that's not yours");
        }
        return donationUserDtoList;
    }


    public List<String> listOfDonors(int campaign_id){
        List<String> donorList = new ArrayList<>();

        String sql = "SELECT username FROM users \n" +
                "JOIN donation ON users.user_id = donation.user_id WHERE campaign_id = ?;";

        SqlRowSet results = template.queryForRowSet(sql, campaign_id);

        while (results.next()){
            donorList.add(mapRowToDonor(results));
        }
        return donorList;

    }

    public List<DonorUserDto> getHallOfFameDonors(){
        List<DonorUserDto> hallOfFamers = new ArrayList<>();

        String sql = "SELECT users.username, SUM(donation.amount) AS total_donation_amount\n" +
                "FROM users\n" +
                "JOIN donation ON users.user_id = donation.user_id\n" +
                "GROUP BY users.username\n" +
                "ORDER BY total_donation_amount DESC\n" +
                "LIMIT 10;";
        SqlRowSet results = template.queryForRowSet(sql);
        while (results.next()){
            hallOfFamers.add(HOFmapRowToDonorUser(results));
        }
        return hallOfFamers;

    }

    public String mapRowToDonor(SqlRowSet rowSet){
        String username = rowSet.getString("username");

        return username;
    }


    public DonorUserDto HOFmapRowToDonorUser(SqlRowSet rowset) {
        String username = rowset.getString("username");

        double amount = rowset.getDouble("total_donation_amount");

        DonorUserDto donation = new DonorUserDto(username, amount);
        return donation;
    }

}
