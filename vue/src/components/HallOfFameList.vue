<template>
    <div>
    <ul class="list-group">
      <li v-for="(person, index) in listOfHall" :key="index" class="list-group-item">
        <div class="row" v-on:click="viewCampaignDetails(person)">
            <div class="col text-capitalize">{{ person.username }}</div>
            <div class="col">${{ person.amount }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
import CampaignService from '../services/CampaignService';
export default {
    data() {
        return {
            listOfHall: [],
        }
    },
    methods: {
        loadData() {
            CampaignService.getHallOfFameDonors().then(res => {
                this.listOfHall = res.data
            
            })
        },
        viewCampaignDetails(person) {
            const user = this.$store.state.user;
            console.log(user)
            if (user && Object.keys(user).length === 0 && user.constructor === Object) {
                this.$router.push({ name: 'login' });
            } else {
                this.$router.push({ name: 'SingleCampaignView', params: { id: person.campaign_id } });
            }
        },
    },
    created() {
        this.loadData()
    }
}
</script>
<style>
.row {
    color: #87ae73;
    max-width: 900px;
    margin: 0 auto;
    margin-bottom: 30px;
    font-size: 20px;
}
</style>