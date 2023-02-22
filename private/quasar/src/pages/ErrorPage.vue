<template>
  <q-page class="flex flex-center">
    <h1>Error</h1>
    <h1>{{ token }}</h1>
  </q-page>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "ErrorPage",
  data() {
    return {
      error: "",
    };
  },
  methods: {
    async getParams() {
      const urlParams = this.$route.params.error;

      
 
    },
  },
  mounted() {
    this.getParams();
  },
});
</script>
