<template>
  Hello Worlds

</template>

<script>
import { defineComponent } from "vue";
import { axios } from "axios";

export default defineComponent({
  name: "IndexPage",
  methods: {

  },

  setup() {
    const get = async () => {
      const response = await axios.get("http://www.pceconomic.me/token");
      console.log(response);
    };
    get();
  },
});
</script>
