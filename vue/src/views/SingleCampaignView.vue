<template>
    <div class="alert alert-success" role="alert" v-if="alert">{{ alert.message }}</div>
    <div>
        <div>
            <CampaignDetails v-bind:campaign="campaignData"/>
        </div>
    </div>
</template>
<script>

import CampaignService from '../services/CampaignService';
import CampaignDetails from '../components/CampaignDetails.vue' 
export default {
    data(){
        return {
            campaignData:[],
            alert: null,
        }
    },
    components: {
        CampaignDetails
    },
    methods: {
        loadData() {
            console.log("this is id:" ,this.$route.params.id )
            CampaignService.getCampaign(this.$route.params.id).then(res => {
                console.log("res data")
                console.log(res.data)
                this.campaignData = res.data;
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
        // check if user logged in
        const user = this.$store.state.user;
        if (user && Object.keys(user).length === 0 && user.constructor === Object) {
            this.$router.push({ name: 'login'});
        }

        this.loadData();
        this.showAlert();
    }
}
</script>
<style>

</style>