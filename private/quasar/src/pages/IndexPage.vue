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
        >
          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn
                icon="edit"
                color="amber-5"
                @click="showEditDialog(props)"
              />
              <q-btn
                icon="delete"
                class="ml-2"
                color="red-14"
                @click="showDeleteDialog(props)"
              />
            </q-td>
          </template>
        </q-table>

        <q-dialog v-model="editDialog" persistent id="dialogUpdate">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Editar marca</div>
            </q-card-section>

            <q-card-section>
              <q-form></q-form>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="editDialog = false"
              />
              <q-btn label="Guardar" color="purple-9" @click="updateMarca()" />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </div>
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import process from "process";
import { defineComponent } from "vue";

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
          name: "rols",
          align: "center",
          label: "Rols",
          field: "rols",
          sortable: true,
        },
        {
          name: "isactive",
          align: "center",
          label: "Esta Actiu",
          field: "isactive",
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
      editDialog: false,
      deleteDialog: false,
    };
  },
  methods: {
    async getPersones() {
      const personesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/persones"
      );
      const personesJson = await personesAxios.data;

      console.log(personesJson);
      /* personesJson.map((p) => {
        const roles = p.rols.map((r) => r.name).join(", "); // Obtener los nombres de los roles separados por coma
        this.rows.push({
          nom: p.nom,
          cognoms: p.cognom1 + " " + p.cognom2,
          email: p.email,
          rols: roles,
          isactive: p.is_actiu ? "Si" : "No",
        });
      }); */
      personesJson.forEach((p) => {
        const roles = p.rols.map((r) => r.name).join(", "); // Obtener los nombres de los roles separados por coma
        this.rows.push({
          nom: p.nom,
          cognoms: p.cognom1 + " " + p.cognom2,
          email: p.email,
          rols: roles,
          isactive: p.is_actiu ? "Si" : "No",
        });
      });
      this.rowsFiltrats = this.rows;
    },
    showEditDialog(props) {
      this.editDialog = true;
      console.log(props.row);
    },
  },
  mounted() {
    this.getPersones();
  },
});
</script>
