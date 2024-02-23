package com.techelevator.dao;

import com.techelevator.model.DonationDto;
import com.techelevator.model.DonorUserDto;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface DonationDao {

     String createDonation(DonationDto donationToCreate);
     List<DonorUserDto> getDonationsByCampaignIdForCreator(int campaign_id, String username);
     void updateBalanceForCampaign(int campaignId, double amount);
     List<DonorUserDto> getDonationsByCampaignId(int campaign_id);
     List<DonorUserDto> getHallOfFameDonors();
     double getBalanceByCampaignId(int campaign_id);
     DonorUserDto mapRowToDonorUser(SqlRowSet rowset);
     String getCreatorByCampaignId(int campaignId);
     String mapRowToDonor(SqlRowSet rowSet);
     DonorUserDto HOFmapRowToDonorUser(SqlRowSet rowset);
}
