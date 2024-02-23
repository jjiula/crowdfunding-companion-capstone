<template>
    <div class="search-box-container">
        <input id="search-box" placeholder="Search by Campaign Name" type="text" v-model="searchQuery" @input="updateSearchQuery"/>
    </div>
</template>

<script>

export default {
    name: 'search-box',
    data() {
        return {
            searchQuery: '',
        }
    },
    methods: {
        updateSearchQuery() {
            // update searchTerm in store so other components can use it
            // this.searchTerm = this.searchTerm.toUpperCase();

            /* DO NOT DO THIS!!!! */
            // this.$store.state.searchTerm = this.searchTerm;
            this.$emit('updateSearchQuery',  this.searchQuery);
        }
    }
}
</script>


<style scoped>
.search-box-container {
    width: 100%;
    display: flex;
    justify-content: center;
    padding-bottom: 10px;
}

.search-input {
    padding: 10px;
    font-size: 16px;
    border-radius: 5px;
    border: 1px solid navy;
}
div.search-box-container>label {
    padding-left: 10px;
    font-weight: bold;
}
</style>