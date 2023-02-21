<template>
  <q-page class="flex flex-center">
    <h1>{{ token }}</h1>
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
        const response = await this.$axios.get(`http://localhost:8000/persones`, {
          headers: {
            "Content-Type": "application/json",
            "X-Requested-With": "XMLHttpRequest",
          },
          withCredentials: true,
        });
        
        this.token = response.data;
      }



/*       if (token) {
        const response = await this.$axios.get(`http://localhost:8000`, {
          headers: {
            "Content-Type": "application/json",
            "X-Requested-With": "XMLHttpRequest",
          },
          withCredentials: true,
        });

        this.token = response.data.display_name;
      }

      if (!token) {
        window.location.href = "";
      } */
    },
  },

  mounted() {
    this.getToken();
  },
});
</script>
