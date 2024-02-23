<template>
    <div class="container">
      <form @submit.prevent="submitForm">
        <div>
          <h1 class="fs-1">Create Campaign</h1>
          <hr>
          <div class="row justify-content-center">
            <div class="col-6">
              <div class="mb-3">
                <label for="username" class="form-label">Username: {{ $store.state.user.username }}</label>
              </div>
              <div class="mb-3">
                <label for="campaignName" class="form-label">Campaign:</label>
                <input class="form-control" type="text" placeholder="Campaign" aria-label="Campaign Name" v-model="formData.campaignName">
              </div>
              <div class="mb-3">
                <label for="campaignImage" class="form-label">Campaign Image</label>
                <CloudinaryComp @image-uploaded="handleImageUploaded" />
              </div>
              <div class="mb-3">
                <label for="campaignType" class="form-label">Type of Campaign</label>
                <select class="form-control" id="campaignType" name="campaignType" v-model="formData.campaignType">
                  <option value="Animals">Animals</option>
                  <option value="Business">Business</option>
                  <option value="Education">Education</option>
                  <option value="Emergency">Emergency</option>
                  <option value="Events">Events</option>
                  <option value="Family">Family</option>
                  <option value="Medical">Medical</option>
                  <option value="Memorial">Memorial</option>
                  <option value="Nonprofit">Nonprofit</option>
                  <option value="Sports">Sports</option>
                  <option value="Travel">Travel</option>
                  <option value="Wishes">Wishes</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" placeholder="Description" aria-label="Description" v-model="formData.description"></textarea>
              </div>
              <div class="mb-3">
                <label for="amountGoal" class="form-label">Goal $</label>
                <input class="form-control" type="text" placeholder="Fundraising Goal $" aria-label="Goal Amount" v-model.number="formData.amountGoal">
              </div>
              <div class="row justify-content-center mb-3">
                <div class="col-3">
                  <button type="submit" class="btn btn-primary">Create</button>
                </div>
                <div class="col-3">
                  <button type="button" class="btn btn-secondary" @click="goToCampaignList">Cancel</button>
                </div>
            </div>       
            </div>
          </div>
        </div>
      </form>
    </div>
  </template>
  

<script>
import campaignService from '../services/CampaignService';
import CloudinaryComp from '../components/CloudinaryComp.vue'


export default {
  components: {
    CloudinaryComp
  },
  data() {
    return {
      formData: {
        username: null,
        campaignName: '',
        campaignImage: '',
        campaignType: '',
        description: '',
        amountGoal: null,
      },
    };
  },
  mounted() {
    this.formData.username = this.$store.state.user.username;
  },
  methods: {
    goToCampaignList(){
        this.$router.push({name: 'home'});
    },
    handleImageUploaded(imageUrl) {
      this.formData.campaignImage = imageUrl;
      console.log("image URL: ", imageUrl)
    },
    submitForm(){
        campaignService.createNewCampaign(this.formData).then(resp => {
            if (resp.status === 201){
                this.$store.commit('SET_NOTIFICATION', {
                    message: 'A new Campaign was added.',
                    type: 'success'
                }
                );
                this.$router.push({name: 'home'});
            }
        }).catch(err => {
            this.handleErrorResponse(err, 'adding');
        })

    },
    handleErrorResponse(error, verb) {
      if (error.response) {
        this.$store.commit('SET_NOTIFICATION',
          "Error " + verb + " card. Response received was '" + error.response.statusText + "'.");
      } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', "Error " + verb + " card. Server could not be reached.");
      } else {
        this.$store.commit('SET_NOTIFICATION', "Error " + verb + " card. Request could not be created.");
      }
    },
  }  
};
</script>