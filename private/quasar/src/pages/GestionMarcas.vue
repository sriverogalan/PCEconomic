<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona tus marcas</h1>

      <div class="q-pa-md">
        <q-table
          title="Usuarios"
          :rows="rowsFiltrats"
          :columns="columns"
          row-key="nom"
          :loading="loading"
          loading-label="Cargando..." 
        >
          <template v-slot:bottom-left>
            <q-btn class="mb-1" color="purple-9" icon="add" />
          </template>

          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn color="amber-5" icon="edit" />
              <q-btn class="ml-2" color="red-14" icon="delete" />
            </q-td>
          </template>
        </q-table>

        <q-dialog v-model="dialog"> </q-dialog>

        <q-dialog v-model="dialog"> </q-dialog>

        <q-dialog v-model="dialog"> </q-dialog>
      </div>
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import { defineComponent } from "vue";

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      loading: true,
      columns: [
        {
          name: "Id",
          required: true,
          label: "id",
          align: "center",
          field: (row) => row.id,
          sortable: true,
        },
        {
          name: "CIF",
          required: true,
          label: "CIF",
          align: "center",
          field: (row) => row.cif,
          sortable: true,
        },
        {
          name: "Nom",
          required: true,
          label: "Nom",
          align: "center",
          field: (row) => row.nom,
          sortable: true,
        },
        {
          name: "actions",
          align: "center",
          label: "Acciones",
          field: "actions",
        },
      ],
      rows: [],
      rowsFiltrats: [],
    };
  },
  methods: {
    async getPersones() {
      const personesAxios = await axios.get("http://localhost:8000/api/get/marques");
      const personesJson = await personesAxios.data;

      console.log(personesJson);
      personesJson.map((p) => {
        this.rows.push({
          id: p.id_marca,
          cif: p.cif,
          nom: p.nom,
        });
        console.log(this.rows);
      });
      this.rowsFiltrats = this.rows;
      this.loading = false;
    },
  },
  mounted() {
    this.getPersones();
  },
});
</script>

<style scoped>
.ml-2 {
  margin-left: 2rem;
}
</style>
