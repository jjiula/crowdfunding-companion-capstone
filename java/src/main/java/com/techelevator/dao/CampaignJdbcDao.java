package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CampaignDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component

public class CampaignJdbcDao implements CampaignDao {

    private JdbcTemplate template;

    public CampaignJdbcDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CampaignDto> getCampaigns() {
        String sql = "SELECT * FROM campaign";
        List<CampaignDto> campaigns = new ArrayList<>();

        SqlRowSet results = template.queryForRowSet(sql);

        while(results.next()) {
            CampaignDto campaign = mapRowToCampaign(results);
            campaigns.add(campaign);
        }

        return campaigns;
    }

    @Override
    public List<CampaignDto> getCampaignsByUsername(String username) {
        String sql = "SELECT * FROM campaign WHERE username = ?";
        List<CampaignDto> campaigns = new ArrayList<>();

        SqlRowSet results = template.queryForRowSet(sql, username);

        while(results.next()) {
            CampaignDto campaign = mapRowToCampaign(results);
            campaigns.add(campaign);
        }

        return campaigns;
    }



    public CampaignDto mapRowToCampaign(SqlRowSet rowset) {
        int id = rowset.getInt("campaign_id");
        String username = rowset.getString("username");
        String campaignName = rowset.getString("campaignName");
        String campaignImage = rowset.getString("campaignImage");
        String campaignType = rowset.getString("campaignType");
        String description = rowset.getString("description");
        double amountGoal = rowset.getDouble("amountGoal");
        double balance = rowset.getDouble("balance");

        CampaignDto campaign = new CampaignDto(id, username, campaignName, campaignImage, campaignType, description, amountGoal, balance);
        return campaign;
    }

    @Override
    public CampaignDto getCampaign(int campaignId) {
        String sql = "SELECT * FROM campaign WHERE campaign_id = ?";
        SqlRowSet result = template.queryForRowSet(sql,campaignId);

        CampaignDto campaign = null;
        if (result.next()) {
            campaign = mapRowToCampaign(result);
        }
        return campaign;
    }
    public int getUserIdByUsername(String username){
        String sql = "SELECT user_id from users where username = ?;";
        return template.queryForObject(sql, Integer.class,username);
    }
    public List<CampaignDto> getCampaignByDonorUserId(String username) {
        int userId = getUserIdByUsername(username);
        String sql = "SELECT * FROM campaign JOIN donation on campaign.campaign_id = donation.campaign_id WHERE user_id = ?;";

        SqlRowSet result = template.queryForRowSet(sql,userId);

        List<CampaignDto> campaigns = new ArrayList<>();

        while (result.next()) {
            CampaignDto campaign = mapRowToCampaign(result);
            campaigns.add(campaign);
        }
        return campaigns;
    }

    @Override
    public CampaignDto getCampaignByType(String campaignType) {
        String sql = "SELECT * FROM campaign WHERE campaigntype = ?;";
        SqlRowSet result = template.queryForRowSet(sql,campaignType);

        CampaignDto campaign = null;
        if (result.next()) {
            campaign = mapRowToCampaign(result);
        }
        return campaign;
    }

    @Override
    public CampaignDto createCampaign(CampaignDto campaignToCreate) {
        String username = campaignToCreate.getUsername();
        String campaignName = campaignToCreate.getCampaignName();
        String campaignImage = campaignToCreate.getCampaignImage();
        String campaignType = campaignToCreate.getCampaignType();
        String description = campaignToCreate.getDescription();
        double amountGoal = campaignToCreate.getAmountGoal();
        double balance = campaignToCreate.getBalance();

        String sql = "INSERT INTO campaign(username, campaignName, campaignImage, campaignType, description, amountGoal, balance) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING campaign_id;";
        int newCampaignID = template.queryForObject(sql, Integer.class, username, campaignName, campaignImage, campaignType, description, amountGoal, balance);

        return getCampaign(newCampaignID);
    }

    @Override
    public void editCampaign(CampaignDto campaignToEdit, String username) {

        try {
            if(getUsernameByCampaignId(campaignToEdit.getCampaign_id()).equals(username)){
                String sql = "UPDATE campaign SET campaignName = ?, campaignImage = ?, campaignType = ?, " +
                        "description = ?, amountGoal = ?, balance = ? WHERE campaign_id = ?;";

                template.update(sql,
                        campaignToEdit.getCampaignName(),
                        campaignToEdit.getCampaignImage(),
                        campaignToEdit.getCampaignType(),
                        campaignToEdit.getDescription(),
                        campaignToEdit.getAmountGoal(),
                        campaignToEdit.getBalance(),
                        campaignToEdit.getCampaign_id());
            }

        } catch (DataAccessException e) {
            throw new DaoException("Cannot Update Campaign that isn't yours");
        }
    }

    @Override
    public void deleteCampaign(String name,int campaignId) {

      try {
          if (getUsernameByCampaignId(campaignId).equals(name)) {
              String sql = "DELETE FROM campaign WHERE campaign_id = ? AND username = ?;";
              template.update(sql, campaignId, name);
          }
      } catch (DataAccessException e){
          throw new DaoException("Cannot delete campaign that isn't yours");
      }


    }

    public String getUsernameByCampaignId(int campaignId){
        String sql = "SELECT username FROM campaign WHERE campaign_id = ?;";

       String username = template.queryForObject(sql, String.class, campaignId);

        return username;
    }
}
