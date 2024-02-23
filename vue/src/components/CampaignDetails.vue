<template>
    <div class="all">
    <div class="campaign-details">
      <img v-bind:src="campaign.campaignImage"/>
      <h2>{{ campaign.campaignName }}</h2>
      <p>Campaign Manager: {{ campaign.username }}</p>
      <p>Campaign Type: {{ campaign.campaignType }}</p>
      <p>What are we raising money for? <br>
        {{campaign.description }}</p>
      <p>Goal: ${{ campaign.amountGoal }}</p>
      <p>Balance: ${{ campaign.balance }}</p>
      <div class="progress" role="progressbar" aria-label="Success example" :aria-valuenow="campaignData.percentage" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar bg-success" :style="{ width: campaignData.percentage + '%' }"></div>
            </div>
      <br>
      <router-link class="btn btn-primary" v-bind:to="{name: 'donation'}">donate</router-link>
      <router-link v-bind:to="{ name: 'EditCampaign', params: { id: $route.params.campaign_id } }" class="btn btn-submit">Edit
      Campaign</router-link>
    <button class="btn btn-cancel" v-on:click="removeCampaign(id)">Delete Campaign</button>
    </div>
    <br>
    <div class="container">
        <button class="propbutton"><router-link class="nav-link" v-bind:to="{ name: 'ProposalList' }">See all the proposals</router-link></button>
        </div>
    <br>
    <div>
        <h4 class="donation-list-header">Donation List:</h4>
        <ul class="list-group list-group-flush" v-for="(donation, index) in donations" v-bind:key="index">
            <li class="list-group-item style-donation-list"> {{ donation.username }} donated ${{ donation.amount }} for this campaign : <a class="comment">{{ donation.donation_comment }}</a></li>
        </ul>
    </div>
</div>
  </template>
  
<script>
import CampaignService from '../services/CampaignService';
// import ProposalsView from '../views/ProposalsView.vue';
import CloudinaryComp from '../components/CloudinaryComp.vue';


  export default {
    components: {
        CloudinaryComp

    },
    data() {
        return {
            selectedCampaign: '',
            donations:{
            },
            campaignData:{},
        };
    },
    props: ['campaign'],
            
    methods: {
        removeCampaign() {
            if (confirm('Are you sure you want to delete this card? This action cannot be undone.')) {
                CampaignService.deleteCampaign(this.campaign.campaign_id).then(resp => {
                    if (resp.status === 204)
                        console.log(resp.status);
                    {
                        this.$store.commit('SET_NOTIFICATION', {
                            message: '',
                            type: 'Success'
                        });
                        this.$router.push({ name: 'home' });
                    }
                }).catch(err => {
                    if (err.response) {
                        this.$store.commit('SET_NOTIFICATION', `Error deleting campaign. Response received was "${err.response.statusText}".`);
                    }
                    else if (err.request) {
                        this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Server could not be reached.');
                    }
                    else {
                        this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Request could not be created.');
                    }
                });
            }
        },
        loadData(){
            console.log("donations: ", this.donations)
            // console.log("hello 2")
            // console.log("hellp 3",this.campaign)
            CampaignService.getDonationForDonor(this.$route.params.id).then(res => {
                console.log(res.status)
                this.donations = res.data
                // console.log("hwer",this.donations)
            });

        },
        loadData2() {
            let id = parseInt(this.$route.params.id);
            CampaignService.getCampaign(id).then(resp => {
                let campaign1 = resp.data;
                let selectedCampaign = campaign1
                if (selectedCampaign) {
                let percentage = (selectedCampaign.balance / selectedCampaign.amountGoal) * 100;

                this.campaignData = {
                    campaign_id: selectedCampaign.campaign_id,
                    username: selectedCampaign.username,
                    description: selectedCampaign.description,
                    amountGoal: selectedCampaign.amountGoal,
                    balance: selectedCampaign.balance,
                    campaignName: selectedCampaign.campaignName,
                    campaignType: selectedCampaign.campaignType,
                    percentage: percentage,
                };

                this.$store.commit('SET_CAMPAIGNS', this.campaignData);
                console.log("print this:", this.campaignData);
                }
            });
         }
        

    },
   async created() {
        this.selectedCampaign = this.$route.params.campaign_id;
        this.loadData();
        this.loadData2();
    },
   
};
  </script>
  <style>
.campaign-details {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid navy;
    border-radius: 5px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 5px rgba(0, 0, 0, .1);
    color: #87ae73;
}
.campaign-details h2 {
    margin-top: 0;
}
.campaign-details p {
    margin: 10px 0;
}
.btn {
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    text-decoration: none;
    background-color: #87ae73;
    color: white;
    border-color: navy;
}
.btn:hover {
  background-color: white;
  color: #87ae73;
  border-color: navy;
}
.donation-list-header{
    text-align: center;
    color: #87ae73;
    font-size: 50px;
}
.propbutton{
    display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; /* Added for vertical centering */
  width: fit-content; /* Optional for precise horizontal centering */
  margin: auto; /* Optional for centering within a larger container */
}
.style-donation-list {
  text-align: center;
  font-size: 1.4em;
  font-weight: bold;
  color: #87ae73;
}
.comment{
    color: #0e0e0e;
    font-size: small;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
}
</style>
  