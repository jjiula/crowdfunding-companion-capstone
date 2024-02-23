<template>
  <div class="all">
    <div class="container">
        <div class="col-4" v-for="(campaign, index) in donationList" v-bind:key="index">        
        <div class="card mb-3" v-on:click="viewCampaignDetails(campaign)">
          <div class="card-header logged-in-card">
            <i class="fa-solid fa-list"></i> {{ campaign.campaignType}}
          </div>
          <div class="card-body text-capitalize logged-in-card">
            <h5 class="card-title">{{ campaign.campaignName}}</h5>
            <img v-bind:src="campaign.campaignImage"/>
            <p class="card-text"><i class="fa-solid fa-bullseye"></i> ${{ campaign.balance }} raised of ${{ campaign.amountGoal }}</p>
            <p class="card-text">{{ campaign.description }}</p>
          </div>
          <div class="card-footer text-body-secondary">
            <div class="row d-flex align-items-center">
              <div class="col text-end">
                <router-link class="btn btn-primary" v-bind:to="{name: 'donation', params: {id: campaign.campaign_id}}">Donation</router-link>
              </div>
            </div>
            A fundraiser organized by: {{ campaign.username }}
          </div>
        </div>  
      </div>
    </div>
  </div>
</template>

<script>
import CampaignService from '../services/CampaignService';

export default {
    data(){
        return {
            donationList: [],
        }
    },
    methods: {
        loadData() {
            console.log("hello")
            CampaignService.getDonationHistory().then(res => {
                this.donationList = res.data;
                console.log(this.donationList)
            })

        },
        
    },
    created() {
        this.loadData();
    }
}
</script>
<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.col-4 {
  max-width: calc(33.33% - 20px);
}

.card {
  width: 100%; 
  box-sizing: border-box; 
}

.card-body {
  display: flex;
  flex-direction: column;
}

.card-title {
  margin-bottom: 10px; 
}

.card-text {
  flex: 1; 
  margin-bottom: 10px;
}

.card-footer {
  margin-top: auto;
}
img{
  flex: 1;
}
</style>
