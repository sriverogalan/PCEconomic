<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona Categories</h1>

      <div class="q-pa-md">
        <q-table
          title="Marcas"
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
              @click="showCreateCategory()"
            >
            </q-btn>
          </template>
        </q-table>

        <q-dialog v-model="dialogCreateCategory" id="dialogCreateCategory">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <span class="q-ml-sm">Crear Categoria.</span>
            </q-card-section>

            <q-card-section>
              <q-form>
                <q-input
                  v-model="nomCategoria"
                  label="Nombre"
                  filled
                  class="q-mb-md"
                />
              </q-form>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="dialogCreateCategory = false"
              />
              <q-btn
                label="Guardar"
                color="purple-9"
                @click="createCategoria()"
              />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog v-model="dialogEdit" persistent id="dialogUpdate">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Editar marca</div>
            </q-card-section>

            <q-card-section> </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="dialogEdit = false"
              />
              <q-btn label="Guardar" color="purple-9" @click="updateMarca()" />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog v-model="dialogDelete" persistent id="dialogDelete">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Eliminar marca</div>
            </q-card-section>

            <q-card-section>
              <p>
                Estas seguro que quieres eliminar la marca
                {{ marcaDelete.nom }} ?
              </p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn
                flat
                label="Cancelar"
                color="red-14"
                @click="dialogDelete = false"
              />
              <q-btn label="Eliminar" color="purple-9" @click="deleteMarca()" />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </div>
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import { defineComponent } from "vue";

const source = axios.CancelToken.source();

export default defineComponent({
  name: "GestionCategorias",
  data() {
    return {
      nomCategoria: "",
      subcategories: [],
      filter: "",
      nomSubcategoria: "",
      categoria: [],
      dialogCreate: false,
      dialogCreateCategory: false,
      dialogCreateSubcategory: false,
      dialogEdit: false,
      dialogDelete: false,
      loading: true,
      categoriaEdit: {
        id_categoria: "",
        nom: "",
        subcategories: [],
      },
      categoriaDelete: {
        id_categoria: "",
        nom: "",
        subcategories: [],
      },
      columns: [
        {
          name: "Id",
          required: true,
          label: "Id",
          align: "center",
          field: (row) => row.id_categoria,
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
    filtrar() {
      this.rowsFiltrats = this.rows.filter((m) => {
        return (
          m.nom.toLowerCase().includes(this.filter.toLowerCase()) ||
          m.id_categoria
            .toString()
            .toLowerCase()
            .includes(this.filter.toLowerCase())
        );
      });
    },
    async getCategories() {
      this.loading = true;
      this.rows = [];
      const categoriesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/categories",
        {
          cancelToken: source.token,
        }
      );
      const categoriesJson = await categoriesAxios.data;
      console.log(categoriesJson);
      this.categories = categoriesJson;

      console.log("Categories", this.categories);
      this.categories.forEach((c) => {
        this.rows.push({
          id_categoria: c.id_categoria,
          nom: c.nom,
        });
      });

      this.rowsFiltrats = this.rows;
      this.loading = false;
    },
    showEditDialog(props) {
      this.dialogEdit = true;
      this.marcaEdit.id_marca = props.row.id_marca;
      this.marcaEdit.nom = props.row.nom;
      this.marcaEdit.cif = props.row.cif;
    },
    showDeleteDialog(props) {
      this.dialogDelete = true;
      this.marcaDelete.id_marca = props.row.id_marca;
      this.marcaDelete.nom = props.row.nom;
      this.marcaDelete.cif = props.row.cif;
    },
    showCreateDialog() {
      this.dialogCreate = true;
    },
    showCreateCategory() {
      this.dialogCreate = false;
      this.dialogCreateCategory = true;
    },
    showCreateSubcategory() {
      this.dialogCreate = false;
      this.dialogCreateSubcategory = true;
    },
    async createCategoria() {
      try {
        this.loading = true;
        this.dialogCreateCategory = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/create/categories",
          {
            nom: this.nomCategoria,
          }
        );
        const sendJson = await sendAxios.data;
        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getCategories();
      }
    },
    async createSubcategoria() {
      try {
        this.loading = true;
        this.dialogCreateSubcategory = false;
        console.log("Nom Subcategoria: ", this.nomSubcategoria);
        console.log("Nom Categoria: ", this.categoria);
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/create/subcategories",
          {
            nom: this.nomSubcategoria,
            id_categoria: this.categoria.value,
          }
        );
        const sendJson = await sendAxios.data;
        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getCategories();
      }
    },
    async updateMarca() {
      try {
        this.loading = true;
        this.dialogEdit = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/update/marques",
          {
            id_marca: this.marcaEdit.id_marca,
            nom: this.marcaEdit.nom,
            cif: this.marcaEdit.cif,
          }
        );
        const sendJson = await sendAxios.data;

        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getMarques();
      }
    },
    async deleteMarca() {
      try {
        this.loading = true;
        this.dialogDelete = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/delete/marques",
          {
            id_marca: this.marcaDelete.id_marca,
          }
        );
        const sendJson = await sendAxios.data;

        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getMarques();
      }
    },
  },
  mounted() {
    this.getCategories();
  },
});
</script>

<style scoped>
.ml-2 {
  margin-left: 2rem;
}

.background {
  background-color: #b1b1b1;
}
.sizeTitleCard {
  width: 350px;
}
</style>
