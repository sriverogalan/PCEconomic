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
                icon="person_add_alt_1"
                color="purple-14"
                @click="addUserAsAdmin(props)"
              >
                <q-tooltip>Añadir cómo Administrador</q-tooltip>
              </q-btn>

              <q-btn
                icon="edit"
                class="ml-2"
                color="amber-5"
                @click="editUserDialog(props)"
              />
              <q-btn
                icon="delete"
                class="ml-2"
                color="red-14"
                @click="deleteUserDialog(props)"
              />
            </q-td>
          </template>

          <template v-slot:top-right>
            <q-input
              color="purple-6"
              v-model="filter"
              rounded
              outlined
              @update:model-value="filtrarSubcategories"
            >
              <template v-slot:prepend>
                <q-icon name="search" />
              </template>
            </q-input>
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
              <q-btn label="Guardar" color="purple-9" @click="updateUser()" />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog
          v-model="addUserAsAdminDialog"
          persistent
          id="addUserAsAdminDialog"
        >
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Añadir cómo Administrador</div>
            </q-card-section>

            <q-card-section>
              <p>
                Estas seguro que quieres que el usuario
                {{ addUserAsAdminObj.nom }} se convierta en administrador?
              </p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="addUserAsAdminDialog = false"
              />
              <q-btn label="Guardar" color="purple-9" @click="addAdminUser()" />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog v-model="deleteDialog" persistent id="deleteDialog">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Eliminar Usuario</div>
            </q-card-section>

            <q-card-section>
              <p>
                Estas seguro que quieres eliminar al usuario
                {{ deleteUserObj.nom }} ?
              </p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="deleteDialog = false"
              />
              <q-btn label="Guardar" color="purple-9" @click="deleteUser()" />
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
          name: "id",
          required: true,
          label: "ID",
          align: "center",
          field: (row) => row.id,
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
      addUserAsAdminDialog: false,
      deleteUserObj: {
        id: "",
        nom: "",
      },
      addUserAsAdminObj: {
        id: "",
        nom: "",
      },
    };
  },
  methods: {
    async getUsers() {
      this.rows = [];
      const personesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/persones"
      );
      const personesJson = await personesAxios.data;

      personesJson.forEach((p) => {
        const roles = p.rols.map((r) => r.name).join(", ");
        this.rows.push({
          id: p.id_persona,
          nom: p.nom,
          cognoms: p.cognom1 + " " + p.cognom2,
          email: p.email,
          rols: roles,
          isactive: p.is_actiu ? "Si" : "No",
        });
      });
      this.rowsFiltrats = this.rows;
    },

    async deleteUser() {
      try {
        this.loading = true;
        this.deleteDialog = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/delete/persones",
          {
            id_persona: this.deleteUserObj.id,
          }
        );
        const sendJson = await sendAxios.data;
        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getUsers();
      }
    },

    async addAdminUser() {
      try {
        this.loading = true;
        this.addUserAsAdminDialog = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/roles/admin/add",
          {
            id_persona: this.addUserAsAdminObj.id,
          }
        );
        const sendJson = await sendAxios.data;
        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getUsers();
      }
    },

    editUserDialog(props) {
      this.editDialog = true;
      console.log(props.row);
    },
    deleteUserDialog(props) {
      this.deleteDialog = true;
      this.deleteUserObj.nom = props.row.nom + " " + props.row.cognoms;
      this.deleteUserObj.id = props.row.id;
    },
    addUserAsAdmin(props) {
      this.addUserAsAdminDialog = true;
      this.addUserAsAdminObj.nom = props.row.nom + " " + props.row.cognoms;
      this.addUserAsAdminObj.id = props.row.id;
    },
  },
  mounted() {
    this.getUsers();
  },
});
</script>
<style>
.ml-2 {
  margin-left: 2rem;
}

.sizeTitleCard {
  width: 350px;
}
</style>
