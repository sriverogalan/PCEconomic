<template>
  <q-page class="flex flex-center">
    <h1>{{ token }}</h1>
    <button @click="getToken">Get Token</button>
  </q-page>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      token: "",
    };
  },
  methods: {
    async getToken() {
      const urlParams = this.$route.params.token;

      if (urlParams) {
        localStorage.setItem("token", urlParams);
      }

      const token = localStorage.getItem("token");

      if (token) {
        await this.$axios.get(`http://localhost:8000/persones`, {}).then(
          (response) => {
            console.log(JSON.parse(response.request.response));
          },
          (error) => {
            console.log(error);
          }
        );
      } 
    },
  },

  setup() {},
});
</script>
