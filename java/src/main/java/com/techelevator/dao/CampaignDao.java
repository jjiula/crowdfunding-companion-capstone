package com.techelevator.dao;

import com.techelevator.model.CampaignDto;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface CampaignDao {

     List<CampaignDto> getCampaigns();
     CampaignDto getCampaign(int campaignId);
     CampaignDto createCampaign(CampaignDto campaignToCreate);
     void editCampaign(CampaignDto campaignToEdit, String username);
     void deleteCampaign(String name,int campaignId);
     CampaignDto mapRowToCampaign(SqlRowSet rowset);
     CampaignDto getCampaignByType(String campaignType);
     List<CampaignDto> getCampaignByDonorUserId(String username);
     List<CampaignDto> getCampaignsByUsername(String username);
}
