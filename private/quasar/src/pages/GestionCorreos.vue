<template lang="">
  <div class="col-10">
    <h1 class="col-12 text-center">Gestiona Correos</h1>
    <div class="q-pa-md">
      <q-table
        title="Usuarios"
        :rows="rowsFiltrats"
        :columns="columns"
        row-key="nom"
      >
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn
              icon="send"
              class="ml-2"
              color="amber-5"
              @click="sendEmail(props)"
            />
          </q-td>
        </template>
      </q-table>
    </div>
  </div>
</template>
<script>
import process from "process";

export default {
  name: "GestionCorreos",
  data() {
    return {
      columns: [
        // Columna id, email y nom propietat
        { name: "id", label: "ID", field: "id", align: "left" },
        { name: "email", label: "Email", field: "email", align: "left" },
        { name: "nom", label: "Nom Propietats", field: "nom", align: "left" },
        {
          name: "actions",
          label: "Acciones",
          field: "actions",
          align: "left",
          sortable: false,
        },
      ],
      rows: [],
    };
  },
  methods: {
    async getCorreos() {
      const response = await this.$axios.get(
        process.env.CRIDADA_API + "api/get/correus"
      );

      const data = response.data;
      console.log(data);

      /* data.forEach((e) => {
        let nom = e.propietats.valors;
        this.rows.push({
          id: e.id,
          email: e.email,
        });
      }); */
    },
  },
  mounted() {
    this.getCorreos();
  },
};
</script>
<style lang=""></style>
