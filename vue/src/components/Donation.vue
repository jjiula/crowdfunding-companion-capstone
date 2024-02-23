<template>
  <div class="all">
 <div class="container">
  <h2>Thank you for your Donation</h2>
      
      <div class="mb-3">
        <label for="email" class="form-label">Email Address</label>
        <input type="email" class="form-control" id="email">
      </div>   
      <div class="mb-3">
        <label for="paymentMethod" class="form-label">Payment Method</label>
        <select class="form-select" id="paymentMethod" >
          <option value="creditCard">Credit Card</option>
          <option value="paypal">PayPal</option>
        </select>
      </div>
      
      <div>
        <div class="mb-3">
          <label for="cardNumber" class="form-label">Credit Card Number/PayPal Account Name</label>
          <input type="text" class="form-control" id="cardNumber">
        </div>
      </div>
    <form @submit.prevent="submitForm" >
      <div class="mb-3">
        <label for="comments" class="form-label">Comments</label>
        <textarea type="Comments" class="form-control" id="Comments" v-model="donation.donation_comment"></textarea>
      </div>

      <label for="amount">Amount:</label>
      <input
        type="number"
        id="amount"
        v-model="amount"
        placeholder="Enter amount"
        required
      />

      <button type="submit">Submit</button>
    </form>

    <div v-if="submitted">
      <p>Amount submitted: {{ amount }}</p>
    </div>
  </div>
</div>
</template>
<script>
import CampaignService from '../services/CampaignService';
export default {
    data() {
        return {
            donation: {
                campaign_id: null,
                user_id: null,
                amount: '',
                donation_comment: '',
            },
            amount: null,
            donation_comment: null,
            submitted: false,
        };
    },
  methods: {
    submitForm() {
        this.donation.amount = this.amount;
        CampaignService.makeDonation(this.donation).then(res => {
          if (res.status === 201){
                this.$store.commit('SET_NOTIFICATION', {
                    message: 'A new donation was added.',
                    type: 'success'
                }
                );
                this.$router.push({name: 'SingleCampaignView'});
            }
        })
        this.submitted = true;
    },
    // loadData(){
    //     CampaignService.getDonationForDonor(this.router.params.id).then(res => {

    //         })
    // }        
  },
  mounted() {
    this.donation.campaign_id    = this.$route.params.id;
    this.donation.user_id        = this.$store.state.user.id;
  },
  // created() {
  //   this.loadData()
  // }
};
</script>