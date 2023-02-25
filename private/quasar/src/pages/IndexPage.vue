<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona tus usuarios</h1>
      <div class="q-pa-md">
        <q-table
          title="Usuarios"
          :rows="rowsFiltrats"
          :columns="columns"
          row-key="nom"
          no-data="no hay na"
        /> 
      </div>
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import { defineComponent } from "vue";
import process from "process";

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      columns: [
        {
          name: "Nom",
          required: true,
          label: "Nom",
          align: "center",
          field: (row) => row.nom, 
          sortable: true,
        },
        {
          name: "cognoms",
          align: "center",
          label: "Cognoms",
          field: "cognoms",
          sortable: true,
        },
        {
          name: "email",
          align: "center",
          label: "Email",
          field: "email",
          sortable: true,
        },
        {
          name: "telefon",
          align: "center",
          label: "Telefon",
          field: "telefon",
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
      const personesAxios = await axios.get(process.env.CRIDADA_API + "persones");
      const personesJson = await personesAxios.data;
      console.log(personesJson);
      personesJson.map((p) => {
        this.rows.push({
          nom: p.nom,
          cognoms: p.cognom1 + " " + p.cognom2,
          email: p.email,
          telefon: p.telefon,
        });
        console.log(this.rows);
      });
      this.rowsFiltrats = this.rows;
    },
  },
  mounted() {
    this.getPersones();
  },
});
</script>
