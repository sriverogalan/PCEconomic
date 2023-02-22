<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona tus usuarios</h1>
      <div class="q-pa-md">
        <q-table title="Usuaris" :rows="rows" :columns="columns" row-key="name" />
      </div>
    </div>
  </q-page>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      columns: [],
      columnsFiltrades: [],
      rows: [],
      rowsFiltrades: [],
    };
  },
  methods: {


  },

  setup() {

    
  },
});
</script>
