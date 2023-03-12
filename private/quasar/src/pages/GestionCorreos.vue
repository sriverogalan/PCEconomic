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
              color="amber-5"
              @click="showSendEmailDialog(props)"
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
            @click="getCorreos()"
          >
          </q-btn>
        </template>
      </q-table>
      <q-dialog v-model="sendEmailDialog" persistent id="sendEmailDialog">
        <q-card class="sizeTitleCard">
          <q-card-section class="row items-center">
            <div class="text-h6">Enviar Correo</div>
          </q-card-section>

          <q-card-section>
            <p>¿Desea enviar un correo electrónico a {{ email }} ?</p>
          </q-card-section>

          <q-card-actions align="right">
            <q-btn
              flat
              label="Cancelar"
              color="red-14"
              @click="sendEmailDialog = false"
            />
            <q-btn icon="send" color="purple-9" @click="sendEmail()" />
          </q-card-actions>
        </q-card>
      </q-dialog>
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
          label: "ID Propietats",
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
      sendEmailDialog: false,
      email: "",
      id_propietats: "",
      loading: false,
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
        this.rows.push({
          id: e.id_correo,
          email: e.email,
          propietats: e.id_propietats,
        });
      });

      this.rowsFiltrats = this.rows;
      this.loading = false;
    },
    showSendEmailDialog(props) {
      this.sendEmailDialog = true;
      this.id_propietats = props.row.propietats;
      this.email = props.row.email;
    },
    async sendEmail() {
      const response = await this.$axios.post(
        process.env.CRIDADA_API + "api/send/email",
        {
          id_propietats: this.id_propietats,
          email: this.email,
        }
      );
      const data = response.data;
      console.log(data);
      this.sendEmailDialog = false;
      this.getCorreos();
    },
  },
  mounted() {
    this.getCorreos();
  },
};
</script>
<style lang=""></style>
