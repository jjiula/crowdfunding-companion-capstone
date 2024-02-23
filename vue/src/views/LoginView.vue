<template>
  <div class="all">
    <div class="container">
    <div>
      <h4 class="welcome">Welcome to Crowdfunding Companion!</h4>
      <p>The easiest way to fundraise and donate to the causes that matter most to you.</p>
      <img src="/newlogo.png" style="max-width: 300px;" alt="">
    </div>
  </div>
  <div id="login">
    <form v-on:submit.prevent="login" class="login-form">
      <h1>Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials" class="error-message">
        Invalid username and password!
      </div>
      <div role="alert" v-if="$route.query.registration" class="success-message">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit" class="submit-button">Sign in</button>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

html, body {
    margin: 0;
    padding: 0;
    height: 100%;
}

.container{
  text-align: center;
  padding-top: 10px;
}

.all{
  background-color: #e5e5f7; opacity: 0.8; 
  background-image:  repeating-radial-gradient( circle at 0 0, transparent 0, #e5e5f7 20px ), repeating-linear-gradient( #F5F5DC, #B2AC88 );
  min-height: 100vh;
}


#login {
  display: flex;
  align-items: center;
  justify-content: center;
  /* height: 50vh; */
  color: navy;
}

.login-form {
  width: 300px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #e0f2e9; /* Light green background color */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-form h1 {
  margin-bottom: 5px;
  font-size: 24px;
  color: navy;
}

.welcome{
  margin-top: 10px;
  
}


.error-message {
  color: #ff0000;
  margin-bottom: 10px;
}

.success-message {
  color: #00ff00;
  margin-bottom: 10px;
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}

input {
  width: calc(100% - 10px);
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-button {
  padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    text-decoration: none;
    background-color: #87ae73;
    color: white;
    border-color: navy;
}

.submit-button:hover {
  background-color: white;
  color: #87ae73;
  border-color: navy;
}

router-link {
  color: navy;
  text-decoration: none;
}

router-link:hover {
  text-decoration: underline;
}
</style>
