<template>
  <q-page class="flex flex-center">
    <h1>{{ token }}</h1>
  </q-page>
</template>

<script>
import axios from "axios";
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
      const response = await axios.get("http://localhost:8080/token", {
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      });
      this.token = response;
      console.log(response);
    },
  },

  mounted() {
    this.getToken();
  },
});
</script>
