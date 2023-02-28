<template lang="">
  <div class="q-pa-md">
    <TabComponent />
    <q-table
      title="Traducciones"
      :rows="rowsFiltrats"
      :columns="columns"
      row-key="nom"
      :loading="loading"
      loading-label="Cargando..."
    >
      <q-inner-loading
        :showing="true"
        label="Please wait..."
        label-class="text-teal"
        label-style="font-size: 1.1em"
      />

      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn icon="edit" color="amber-5" @click="showEditDialog(props)" />
          <q-btn
            icon="delete"
            class="ml-2"
            color="red-14"
            @click="showDeleteDialog(props)"
          />
        </q-td>
      </template>

      <template v-slot:top-right>
        <q-input
          color="purple-6"
          v-model="filter"
          rounded
          outlined
          @update:model-value="filtrar"
        >
          <template v-slot:prepend>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>

      <template v-slot:top-left>
        <q-btn
          class="mb-1"
          color="purple-9"
          icon="add"
          @click="showCreateDialog()"
        >
        </q-btn>
      </template>

      <template v-slot:bottom-left>
        <q-btn
          class="mb-1"
          color="purple-9"
          icon="add"
          @click="showCreateDialog()"
        >
        </q-btn>
      </template>
    </q-table>
  </div>
</template>
<script>
import axios from "axios";
import TabComponent from "src/components/TabComponent.vue";
import { defineComponent } from "vue";

const source = axios.CancelToken.source();

export default defineComponent({
  name: "GestionTraducciones",
  components: {
    TabComponent,
  },
  data() {
    return {};
  },
  methods: {
    async getTraducciones() {},
  },
  mounted() {
    this.getTraducciones();
  },
  setup() {
    return {
      tab: "static",
    };
  },
});
</script>
<style></style>
