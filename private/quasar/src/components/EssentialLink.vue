<template>
  <q-item clickable :to="link">
    <q-item-section v-if="icon" avatar>
      <q-icon :name="icon" color="purple-14" />
    </q-item-section>

    <q-item-section>
      <q-item-label color="purple-14">{{ title }}</q-item-label>
    </q-item-section>
  </q-item>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "EssentialLink",
  props: {
    title: {
      type: String,
      required: true,
    },

    caption: {
      type: String,
      default: "",
    },

    link: {
      type: String,
      default: "#",
    },

    icon: {
      type: String,
      default: "",
    },
  },
});
</script>
