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
            icon="refresh"
            @click="getFactures()"
          >
          </q-btn>
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
        {
          name: "propietats",
          label: "Nom Propietats",
          field: "propietats",
          align: "left",
        },
        {
          name: "actions",
          label: "Acciones",
          field: "actions",
          align: "left",
          sortable: false,
        },
      ],
      rows: [],
      rowsFiltrats: [],
      filter: "",
    };
  },
  methods: {
    filtrarPerCorreu() {
      this.rowsFiltrats = this.rows.filter((e) => {
        return e.email.toLowerCase().includes(this.filter.toLowerCase());
      });
    },
    async getCorreos() {
      this.rows = [];
      this.loading = true;
      const response = await this.$axios.get(
        process.env.CRIDADA_API + "api/get/correus"
      );

      const data = response.data;
      console.log(data);

      data.forEach((e) => {
        let props_valors = "";
        e.propietats.valors.forEach((p) => {
          props_valors += p.propietat[0].nom + " " + p.valor;

          console.log(p);
        });
        this.rows.push({
          id: e.id_correo,
          email: e.email,
          propietats: props_valors,
        });
      });

      this.rowsFiltrats = this.rows;
    },
  },
  mounted() {
    this.getCorreos();
  },
};
</script>
<style lang=""></style>
