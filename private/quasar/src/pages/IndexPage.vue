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
      const response = await axios.get("https://jsonplaceholder.typicode.com/todos/1");
      console.log(response);
    };
    get();
  },
});
</script>
