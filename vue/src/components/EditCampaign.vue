<template>
  <div class="all">
        <div class="container">
            <div class="container">
                    <h2>Edit Form</h2>
                    <form @submit.prevent="submitForm">
                        <div class="mb-3">
                        <label for="campaign_id" class="form-label">Campaign ID: </label>
                        {{ editCampaign.campaign_id }}
                        </div>
                        <div class="mb-3">
                        <label for="username" class="form-label">Creator: </label>
                          {{  editCampaign.username }}
                        </div>              
                        <div class="mb-3">
                        <label for="campaignName" class="form-label">Campaign Name</label>
                        <input v-model="editCampaign.campaignName" type="text" class="form-control" id="campaignName" required>
                        </div>
                
                        <div class="mb-3">
                        <label for="campaignType" class="form-label">Campaign Type</label>
                        <select v-model="editCampaign.campaignType" class="form-select" id="campaignType" required>
                                                        <option value="Animals" >Animals</option>
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
                        <label for="image" class="form-label">Image</label>
                        <cloudinaryComp @image-uploaded="handleImageUploaded"/>
                      </div>

                        <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea v-model="editCampaign.description" class="form-control" id="description" required></textarea>
                        </div>
                
                        <div class="mb-3">
                        <label for="amountGoal" class="form-label">Amount Goal</label>
                        <input v-model="editCampaign.amountGoal" type="number" class="form-control" id="amountGoal" required>
                        </div>
                
                        <button type="submit" class="btn btn-primary">Submit</button> 
                        <button  class="btn btn-primary" v-on:click="cancelForm" type="button">Cancel</button>
                    </form>
        </div>
    </div>
  </div>
</template>
<script>
import CloudinaryComp from './CloudinaryComp.vue';
import CampaignService from '../services/CampaignService';
export default {
  components: {
    CloudinaryComp
  },
  props: {
    formData: {
      type: Object,
      required: true
    },
  },

  data() {
    return {  
        editCampaign: {
            campaign_id: this.formData.campaign_id,
            username: this.formData.username,
            campaignName: this.formData.campaignName,
            campaignImage: this.formData.campaignImage,
            campaignType: this.formData.campaignType,
            description: this.formData.description,
            amountGoal: this.formData.amountGoal,
            balance: this.formData.balance
        },
    };
  },

  methods: {
    submitForm() {
        console.log('Submitting form:', this.editCampaign);
        CampaignService.editCampaign(this.editCampaign)
        .then(resp => {
            console.log('Response from service:', resp);
            console.log('Updated data from server:', resp.data);

            if (resp.status === 200) {
                this.$store.commit(
                    'SET_NOTIFICATION',
                    {
                        message: ``,
                        type: 'success'
                    }
                );
                this.$router.push({name: 'home'});
            }
        })
        .catch(error => {
            this.handleErrorResponse(error, 'updating');
        })
    },
    cancelForm(){
        this.$router.push({name: 'home'})
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
    handleImageUploaded(imageUrl) {
      this.editCampaign.campaignImage = imageUrl;
      console.log("image URL: ", imageUrl)
    },
  },
};
</script>