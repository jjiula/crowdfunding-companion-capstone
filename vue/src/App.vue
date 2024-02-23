<template>
  <div id="capstone-app">
    <nav class="navbar navbar-expand-lg bg-body-tertiary alo">
      <div class="container">
        <router-link class="navbar-brand" v-bind:to="{ name: 'home' }"><img src="/newlogo.png" style="max-width: 30px;" alt="">Crowdfunding Companion</router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-between" id="navbarNavDropdown">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{ name: 'home' }">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{name:'campaignCreate'}">Create Campaign</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Hall Of Fame</router-link>
              <router-link class="nav-link" v-bind:to="{ name: 'HallOfFame' }" v-if="$store.state.token != ''">Hall Of Fame</router-link>
              <!-- <router-link class="nav-link" v-bind:to="{name: 'HallOfFame'}">Hall of Fame</router-link> -->
            </li>  
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{name: 'FAQ'}">FAQ</router-link>
            </li>                    
          </ul>
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Your Donations</router-link>
              <router-link class="nav-link" v-bind:to="{ name: 'DonationHistory' }" v-if="$store.state.token != ''">Your Donations</router-link>
              <!-- <router-link class="nav-link" v-bind:to="{name: 'DonationHistory'}">Your Donations</router-link> -->
            </li>
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Your Campaigns</router-link>
              <router-link class="nav-link" v-bind:to="{ name: 'yourCampaign' }" v-if="$store.state.token != ''">Your Campaigns</router-link>
            </li>   
            <li class="nav-item">
              <router-link class="nav-link" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Sign In</router-link>
              <router-link class="nav-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav >
    <router-view />
  </div>
</template>
<script>

export default {
  name: 'App',
  components: {
   
  }
}
</script>
<style scoped>
.navbar-brand {
  color: #87ae73;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
 
}
img {
  margin-right: 5px;
  margin-top: 15px;
}

</style>
