<template>
  <div>
    <button @click.prevent="upload">Upload</button><br>
    <img :src="imageUrl" alt="Uploaded Image" v-if="imageUrl">
  </div>
</template>

<script>
import CampaignService from '../services/CampaignService';
export default {
  name: 'CloudinaryComp',
  data() {
    return {
      myWidget: {},
      imageUrl: ''
    };
  },
  methods: {
    upload() {
      this.myWidget.open();
    },
    handleUploadSuccess(result) {
    console.log('handleUploadSuccess triggered:', result);
      if (result && result.event === 'success') {
        this.imageUrl = result.info.url;
        console.log('Image URL:', this.imageUrl);
        this.$emit('image-uploaded', this.imageUrl);

        // call a method in CampaignService.js to update the row 
        // in the database with the image link (this.imageUrl or result.info.url)


        // the component that displays the campaign needs to have a created section 
        // that pulls the image link from the database.

      }
    }

  },
  mounted() {
  this.myWidget = window.cloudinary.createUploadWidget(
    {
      cloudName: 'dpeinoy49',
      uploadPreset: 'xpd4fy7q'
    },
    (error, result) => {
      if (error) {
        console.error('Error during upload:', error);
      } else if (result && result.event === 'success') {
        this.handleUploadSuccess(result);
      }
    }
  );
}

}

</script>

<style scoped>

</style>
