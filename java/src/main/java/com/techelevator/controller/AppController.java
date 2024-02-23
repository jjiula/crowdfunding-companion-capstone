package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*")
public class AppController {

    private CampaignDao dao;
    private ProposalDao propdao;
    private DonationDao donationDao;
    private VoteDao voteDao;

    public AppController(CampaignDao dao, ProposalDao propdao, DonationDao donationDao, VoteDao voteDao) {
        this.dao = dao;
        this.propdao = propdao;
        this.donationDao = donationDao;
        this.voteDao = voteDao;

    }



    @PreAuthorize("permitAll()")
    @RequestMapping(path="/all-campaigns", method = RequestMethod.GET)
    public List<CampaignDto> getAllCampaigns() {
        return dao.getCampaigns();
    }

    @RequestMapping(path="/campaigns-donated-to", method = RequestMethod.GET)
    public List<CampaignDto> getCampaignByDonorUserId(Principal principal) {
        return dao.getCampaignByDonorUserId(principal.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path="/campaigns-created", method = RequestMethod.GET)
    public List<CampaignDto> getCampaignsByUsername(Principal principal) {
        return dao.getCampaignsByUsername(principal.getName());
    }


    @RequestMapping(path="/campaign", method = RequestMethod.GET)
    public CampaignDto getCampaign(@RequestParam("campaign_id") int campaign_id) {
        return dao.getCampaign(campaign_id);
    }
    @RequestMapping(path="/campaign-type", method = RequestMethod.GET)
    public CampaignDto getCampaignByType(@RequestParam("campaigntype") String campaigntype) {
        return dao.getCampaignByType(campaigntype);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/create-campaign", method = RequestMethod.POST)
    public CampaignDto createCampaign(@Valid @RequestBody CampaignDto campaign) {
        return dao.createCampaign(campaign);
    }

    @RequestMapping(path="/edit-campaign", method = RequestMethod.PUT)
    public void editCampaign(@Valid @RequestBody CampaignDto campaign, Principal principal) {
        try {
            dao.editCampaign(campaign, principal.getName());
         ResponseEntity.ok("Campaign updated successfully");
    } catch (DaoException e) {
         ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    }

    @RequestMapping(path="/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(Principal principal, @RequestParam("campaign_id") int campaign_id) {

        try {
            dao.deleteCampaign(principal.getName(), campaign_id);
        } catch (DaoException e) {
             ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(path="/proposal", method = RequestMethod.GET)
    public ProposalDto getProposal(@RequestParam("proposal_id") int proposal_id) {
        return propdao.getProposal(proposal_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/create-proposal", method = RequestMethod.POST)
    public ProposalDto createProposal(@Valid @RequestBody ProposalDto proposal, @RequestParam("campaign_id") int campaignId) {
        return propdao.createProposal(proposal, campaignId);
    }

    @RequestMapping(path="/edit-proposal", method = RequestMethod.PUT)
    public void editProposal(@Valid @RequestBody ProposalDto proposal, Principal principal) {
        try {
            propdao.editProposal(proposal, principal.getName());
            ResponseEntity.ok("Proposal updated successfully");
        } catch (DaoException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(path="/delete-proposal", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProposal(Principal principal, @RequestParam("proposal_id") int proposal_id, @RequestParam("campaign_id") int campaign_id) {

        try {
            propdao.deleteProposal(principal.getName(), proposal_id, campaign_id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @RequestMapping(path = "/getProposalListByCampaignId", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProposalDto> getProposalListByCampaignId(Principal principal, @RequestParam("campaign_id") int campaignId){

        try {
          return   propdao.getProposalByCampaignId(principal.getName(), campaignId);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }

    @RequestMapping(path = "/create-donation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createDonation(@RequestBody DonationDto donationDto){

        try {
        return donationDao.createDonation(donationDto);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @RequestMapping(path = "/get-donations-list-by-campaign-id/creator", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DonorUserDto> getDonationsByCampaignIdForCreator(@RequestParam("campaign_id") int campaign_id, Principal principal){


     try {
         return donationDao.getDonationsByCampaignIdForCreator(campaign_id, principal.getName());
     } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }
    @RequestMapping(path = "/get-donations-list-by-campaign-id", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DonorUserDto> getDonationsByCampaignIdForDonor(@RequestParam("campaign_id") int campaign_id){
        try {
            return donationDao.getDonationsByCampaignId(campaign_id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }

    @RequestMapping(path = "/add-vote", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addVote(@RequestBody VoteDto voteDto, Principal principal, @RequestParam("campaign_id") int campaign_id) {
        try {
            voteDao.addVote(voteDto, principal.getName(), campaign_id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @RequestMapping(path = "/update-vote", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateVote(@RequestParam("proposalId") int proposalId){
        System.out.println(proposalId);
        voteDao.updateProposalStatus(proposalId);
    }

    @RequestMapping(path = "/hall-of-fame", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DonorUserDto> getHallOfFameDonors(){
        return donationDao.getHallOfFameDonors();
    }

}

