<template lang="">
  <div>
    <q-tabs v-model="tab" class="bg-purple text-white shadow-2">
      <q-tab name="static" label="Traducciones Estáticas" />
      <q-tab name="dynamic" label="Traduccions Dinámicas" />
    </q-tabs>
  </div>
</template>
<script>
export default {
  name: "TabComponent",
};
</script>
<style lang=""></style>
