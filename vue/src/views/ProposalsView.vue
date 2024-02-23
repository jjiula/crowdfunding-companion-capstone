<template>
  <div class="all">
    <router-link v-bind:to="{ name: 'SingleCampaignView' }">Go back </router-link>
    <div class="row">
      <div class="col-md-4" v-for="proposal in proposals" :key="proposal.id">
        <div class="card mb-3">
          <div class="card-body">
            <h5 class="card-title">{{ proposal.description }}</h5>
            <p class="card-text">Description: {{ proposal.proposal_name }}</p>
            <p class="card-text"><strong>Status:</strong> {{ proposal.proposal_status }}</p>
            <p class="card-text"><strong>Deadline:</strong> {{ proposal.proposal_deadline }}</p>
            <p class="card-text"><strong>Vote Passed:</strong> {{ proposal.vote_passed }}</p>
            <div class="btn-group" role="group" aria-label="Vote">
              <button type="button" class="btn btn-success" @click="createVote(proposal.proposal_id, 'true')">Yes</button>
              <button type="button" class="btn btn-danger" @click="createVote(proposal.proposal_id, 'false')">No</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <button><router-link class="nav-link" v-bind:to="{ name: 'CreateProposal' }">Make a new Proposal</router-link></button>
    </div>
</template>
<script>
import CampaignService from '../services/CampaignService';

export default {
    data() {
        return {
            proposals: [],
            vote: {
              proposal_id: '',
              vote_response: '',
            }
        }
    },
    methods: {
        loadData() {
            CampaignService.getAllProposals(this.$route.params.id).then(res => {
              let data = res.data;

              // Update Proposal Status
              data.forEach(proposal => {
                console.log(proposal)
                CampaignService.updateProposalStatus(proposal.proposal_id);
              });

              CampaignService.getAllProposals(this.$route.params.id).then(res => {
                this.proposals = res.data
              });

            });
    
        },
        createVote(proposal_id, vote_response){
          console.log("alo",this.$route.params.id)
          this.vote.proposal_id = proposal_id
          this.vote.vote_response = vote_response
          CampaignService.createVote(this.$route.params.id, this.vote)
          .then(res => {
            if(res.status == 201){
              this.$store.commit('SET_NOTIFICATION', {
                    message: 'Your vote was added/updated.',
                    type: 'success',
                    timeout: 3000
                }
                );
                this.$router.push({name: 'SingleCampaignView'});
            
            }
          })
        }

    },
    created() {
      this.loadData();
    }
}
</script>
