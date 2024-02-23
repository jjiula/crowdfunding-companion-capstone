<template>
    
    <div class="alert alert-success" role="alert" v-if="alert">{{ alert.message }}</div>
    <div>
        <div>
            <CampaignsList :campaigns="campaignData"/>
        </div>       
    </div>
</template>
<script>

import CampaignsList from '../components/CampaignsList.vue';
import CampaignService from '../services/CampaignService';

export default {
    data(){
        return {
            campaignData:[],
            alert: null,
        }
    },
    components: {
        CampaignsList
    },
    computed: {
        campaigns(){
            return this.$store.state.campaigns;
        },
    },

    methods: {
        loadData() {
            console.log("This is loadData in CampaignView")
            CampaignService.getCampaigns().then(resp => {                
                // this.campaignData = resp.data;                
                let campaigns = resp.data
                campaigns.forEach(campaign => {
                    let percentage = (campaign.balance / campaign.amountGoal) * 100;

                    let data = {
                        campaign_id: campaign.campaign_id,
                        username: campaign.username,
                        campaignImage: campaign.campaignImage,
                        description: campaign.description,
                        amountGoal: campaign.amountGoal,
                        balance: campaign.balance,
                        campaignName: campaign.campaignName,
                        campaignType: campaign.campaignType,
                        percentage: percentage,
                    };

                    this.campaignData.push(data);
                });

                this.$store.commit('SET_CAMPAIGNS', this.campaignData);
                console.log(this.campaignData)
            });
            
        },
        showAlert() {
            // get alert message from STORE
            this.alert = this.$store.state.notification

            if(this.alert == null)
                return

            // get timeout for alert
            let alertShowTimeout = this.alert.timeout

            // use func Timeout to clear the alert display
            setTimeout(() => {
                this.alert = null
            }, alertShowTimeout)
            
        }
    },
    created() {
        this.loadData();
        this.showAlert();
    }
}
</script>