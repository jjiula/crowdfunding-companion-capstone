<template>
    <div class="all">
    <div class="HallOfFame">
  <h1>Donor Hall of Fame</h1>
  <HallOfFameList />
</div>
</div>
</template>

<script>
import HallOfFameList from '../components/HallOfFameList.vue';
import CampaignService from '../services/CampaignService';
export default {
    components: { HallOfFameList }
}
</script>

<style>
.HallOfFame {
    color: #87ae73;
    max-width: 900px;
    margin: 0 auto;
    margin-bottom: 30px;
}
</style>