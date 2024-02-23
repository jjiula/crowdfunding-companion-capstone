<template>
    <CloudinaryComp msg="Welcome to Your Vue.js App"/>
  </template>
  
  <script>
  import CloudinaryComp from '../components/CloudinaryComp.vue'
  
  export default {
    name: 'App',
    components: {
      CloudinaryComp
    }
  }
  </script>
  
  <style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /* margin-top: 5px; */
    
  }
  </style>