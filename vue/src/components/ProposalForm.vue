<template>
    <div class="all">
    <router-link class="nav-link" v-bind:to="{ name: 'ProposalList' }">Go back</router-link>
    <div class="container">
        <form @submit.prevent="save" v-show="!isSaving">
            <div class="mb-3">
                <label for="proposalId" class="form-label">campaign_id</label>
                {{this.$route.params.id }}
                <!-- <input v-model="campaign_id" type="text" class="form-control" id="proposalId" > -->
                </div>
                <div class="mb-3">
                    <label for="proposalName" class="form-label">Proposal Name</label>
                    <input v-model="newProposal.proposal_name" type="text" class="form-control" id="proposalName" >
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea v-model="newProposal.description" class="form-control" id="description" rows="3"></textarea>
                </div>

                <div class="mb-3">
                    <label for="proposalStatus" class="form-label">Proposal Status</label>
                    <select v-model="newProposal.proposal_status" class="form-select" id="proposalStatus" >
                    <option value="pending">Pending</option>
                    <!-- <option value="approved">Approved</option>
                    <option value="rejected">Rejected</option> -->
                    </select>
                </div>
                <div class="mb-3">
                    <label for="deadline" class="form-label">Deadline</label>
                    <input v-model="newProposal.proposal_deadline" type="date" v-bind:min="todaysDate" class="form-control" id="deadline" required>
                </div>

                <button type="submit" class="btn btn-primary">Submit Proposal</button>
                <button class="btn btn-primary" v-bind:to="{ name: 'ProposalList' }">Cancel</button>
            </form>
  </div>
</div>
</template>
<script>
import CampaignService from '../services/CampaignService';
export default {
    data() {
        return {
            campaign_id: this.$route.params.id,
            newProposal: {
            },
            isSaving: false,
            todaysDate: new Date().toISOString().split("T")[0]
        };
    },
    methods: {        
        save() {
                this.isSaving = true;
                console.log("Test")
                console.log("ID=", this.$route.params.id)
                CampaignService.makeProposal(this.$route.params.id, this.newProposal)         
                .then(res => {
                    console.log("hello")   
                    console.log(this.newProposal)
                    
                    if (res.status === 201) {
                        this.$store.commit('SET_NOTIFICATION', {
                        message: 'A new proposal was made successfully.',
                        type: 'success'
                        });
                        this.$router.push({name: 'ProposalList'})
                    }
                })
        }
    },
    
    components: {
        
    },
}
</script>