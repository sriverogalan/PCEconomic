<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona Categories</h1>
      <div class="q-pa-md">
        <q-tabs
          v-model="tab"
          dense
          class="text-grey"
          active-color="primary"
          indicator-color="primary"
          align="justify"
          narrow-indicator
        >
          <q-tab name="categories" label="Categories" />
          <q-tab name="subcategories" label="Subcategories" />
        </q-tabs>

        <q-tab-panels v-model="tab" animated>
          <q-tab-panel name="categories">
            <q-table
              title="Categorias"
              :rows="catRowsFiltrats"
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
                  @update:model-value="filtrarCategories"
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
                  @click="showCreateCategory()"
                />
                <q-btn
                  class="mb-1 ml-1 ml-2"
                  color="amber-14"
                  icon="refresh"
                  @click="getCategories()"
                />
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
                  <div class="text-h6">Editar categoria</div>
                </q-card-section>

                <q-card-section>
                  <q-form>
                    <q-input
                      v-model="categoriaEdit.nom"
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
                    @click="dialogEdit = false"
                  />
                  <q-btn
                    label="Guardar"
                    color="purple-9"
                    @click="updateCategoria()"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>

            <q-dialog v-model="dialogDelete" persistent id="dialogDelete">
              <q-card class="sizeTitleCard">
                <q-card-section class="row items-center">
                  <div class="text-h6">Eliminar categoria</div>
                </q-card-section>

                <q-card-section>
                  <p>
                    Estas seguro que quieres eliminar la categoria
                    {{ categoriaDelete.nom }} ?
                  </p>
                </q-card-section>

                <q-card-actions align="right">
                  <q-btn
                    flat
                    label="Cancelar"
                    color="red-14"
                    @click="dialogDelete = false"
                  />
                  <q-btn
                    label="Eliminar"
                    color="purple-9"
                    @click="deleteCategoria()"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>
          </q-tab-panel>

          <q-tab-panel name="subcategories">
            <q-table
              title="Categorias"
              :rows="subcatRowsFiltrats"
              :columns="subcatColumns"
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
                    @click="showEditSubcategoryDialog(props)"
                  />
                  <q-btn
                    icon="delete"
                    class="ml-2"
                    color="red-14"
                    @click="showDeleteSubcategoryDialog(props)"
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

              <template v-slot:top-left>
                <q-btn
                  class="mb-1"
                  color="purple-9"
                  icon="add"
                  @click="showCreateSubcategory()"
                />
                <q-btn
                  class="mb-1 ml-2"
                  color="amber-14"
                  icon="refresh"
                  @click="getSubcategories()"
                />
              </template>
            </q-table>

            <q-dialog
              v-model="dialogCreateSubcategory"
              id="dialogCreateSubcategory"
            >
              <q-card class="sizeTitleCard">
                <q-card-section class="row items-center">
                  <span class="q-ml-sm">Crear Subcategoria.</span>
                </q-card-section>

                <q-card-section>
                  <q-form>
                    <q-input
                      v-model="nomSubcategoria"
                      label="Nombre"
                      filled
                      class="q-mb-md"
                    />

                    <q-select
                      v-model="categoria"
                      :options="categoriesOptions"
                      label="Categoria"
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
                    @click="dialogCreateSubcategory = false"
                  />
                  <q-btn
                    label="Guardar"
                    color="purple-9"
                    @click="createSubcategoria()"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>

            <q-dialog
              v-model="dialogEditSubcategory"
              id="dialogEditSubcategory"
            >
              <q-card class="sizeTitleCard">
                <q-card-section class="row items-center">
                  <span class="q-ml-sm">Editar Subcategoria.</span>
                </q-card-section>

                <q-card-section>
                  <q-form>
                    <q-input
                      v-model="subcategoriaEdit.nom"
                      label="Nombre"
                      filled
                      class="q-mb-md"
                    />

                    <q-select
                      v-model="categoria"
                      :options="categoriesOptions"
                      label="Categoria"
                      filled
                      class="q-mb-md"
                      :rules="[(val) => val !== null || 'Campo requerido']"
                    />
                  </q-form>
                </q-card-section>

                <q-card-actions align="right">
                  <q-btn
                    flat
                    label="Cancelar"
                    color="red-14"
                    @click="dialogEditSubcategory = false"
                  />
                  <q-btn
                    label="Guardar"
                    color="purple-9"
                    @click="updateSubcategoria()"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>

            <q-dialog
              v-model="dialogDeleteSubcategory"
              persistent
              id="dialogDeleteSubcategory"
            >
              <q-card class="sizeTitleCard">
                <q-card-section class="row items-center">
                  <div class="text-h6">Eliminar Subcategoria</div>
                </q-card-section>

                <q-card-section>
                  <p>
                    Estas seguro que quieres eliminar la subcategoria
                    {{ subcategoriaDelete.nom }} ?
                  </p>
                </q-card-section>

                <q-card-actions align="right">
                  <q-btn
                    flat
                    label="Cancelar"
                    color="red-14"
                    @click="dialogDeleteSubcategory = false"
                  />
                  <q-btn
                    label="Eliminar"
                    color="purple-9"
                    @click="deleteSubcategoria()"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>
          </q-tab-panel>
        </q-tab-panels>
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
      tab: "categories",
      nomCategoria: "",
      subcategories: [],
      filter: "",
      nomSubcategoria: "",
      categoria: [],
      dialogCreateCategory: false,
      dialogEdit: false,
      dialogDelete: false,
      loading: true,
      categoriaEdit: {
        id_categoria: "",
        nom: "",
      },
      categoriaDelete: {
        id_categoria: "",
      },
      subcatColumns: [
        {
          name: "Id",
          required: true,
          label: "Id",
          align: "center",
          field: (row) => row.id_subcategoria,
          sortable: true,
        },
        {
          name: "category",
          required: true,
          label: "Categoria",
          align: "center",
          field: (row) => row.nomCategoria,
          sortable: true,
        },
        {
          name: "subcategory",
          required: true,
          label: "Subcategoria",
          align: "center",
          field: (row) => row.subcategoria,
          sortable: true,
        },
        {
          name: "actions",
          align: "center",
          label: "Acciones",
          field: "actions",
        },
      ],
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
          name: "Categoria",
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
      catRows: [],
      catRowsFiltrats: [],
      subcatRows: [],
      subcatRowsFiltrats: [],
      categoriesOptions: [],
      subcategoriaEdit: {
        id_subcategoria: "",
        nom: "",
        categoria: [],
      },
      subcategoriaDelete: {
        id_subcategoria: "",
        nom: "",
      },
      dialogCreateSubcategory: false,
      dialogEditSubcategory: false,
      dialogDeleteSubcategory: false,
    };
  },
  methods: {
    filtrarCategories() {
      this.catRowsFiltrats = this.catRows.filter((m) => {
        return (
          m.nom.toLowerCase().includes(this.filter.toLowerCase()) ||
          m.id_categoria
            .toString()
            .toLowerCase()
            .includes(this.filter.toLowerCase())
        );
      });
    },
    filtrarSubcategories() {
      this.subcatRowsFiltrats = this.subcatRows.filter((m) => {
        return (
          m.nomCategoria.toLowerCase().includes(this.filter.toLowerCase()) ||
          m.subcategoria.toLowerCase().includes(this.filter.toLowerCase()) ||
          m.id_subcategoria
            .toString()
            .toLowerCase()
            .includes(this.filter.toLowerCase())
        );
      });
    },
    async getCategories() {
      this.loading = true;
      this.catRows = [];
      const categoriesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/categories",
        {
          cancelToken: source.token,
        }
      );
      const categoriesJson = await categoriesAxios.data;
      this.categories = categoriesJson;
      this.categories.forEach((c) => {
        if (c.is_active === 1)
          this.catRows.push({
            id_categoria: c.id_categoria,
            nom: c.nom,
          });
      });

      this.catRowsFiltrats = this.catRows;
      this.loading = false;

      this.categoriesOptions = [];
      this.categories.forEach((c) => {
        if (c.is_active === 1)
          this.categoriesOptions.push({
            label: c.nom,
            value: c.id_categoria,
          });
      });
    },
    showEditDialog(props) {
      this.dialogEdit = true;
      this.categoriaEdit.id_categoria = props.row.id_categoria;
      this.categoriaEdit.nom = props.row.nom;
    },
    showDeleteDialog(props) {
      this.dialogDelete = true;
      this.categoriaDelete.id_categoria = props.row.id_categoria;
    },
    showCreateCategory() {
      this.dialogCreateCategory = true;
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
        this.getSubcategories();
      }
    },
    async updateCategoria() {
      try {
        this.loading = true;
        this.dialogEdit = false;
        console.log("Categoria Edit", this.categoriaEdit);
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/update/categories",
          {
            id_categoria: this.categoriaEdit.id_categoria,
            nom: this.categoriaEdit.nom,
          }
        );
        const sendJson = await sendAxios.data;

        console.log("UpdateCategoria", sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getCategories();
      }
    },
    async deleteCategoria() {
      try {
        this.loading = true;
        this.dialogDelete = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/delete/categories",
          {
            id_categoria: this.categoriaDelete.id_categoria,
          }
        );
        const sendJson = await sendAxios.data;
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getCategories();
      }
    },

    /* 
    
    SUBCATEGORIES

    */

    async getSubcategories() {
      this.loading = true;
      this.subcatRows = [];
      const subcategoriesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/subcategories",
        {
          cancelToken: source.token,
        }
      );
      const subcategoriesJson = await subcategoriesAxios.data;

      this.subcategories = subcategoriesJson;
      this.subcategories.forEach((c) => {
        if (c.is_active === 1)
          this.subcatRows.push({
            id_subcategoria: c.id_subcategoria,
            nomCategoria: c.categories.nom,
            subcategoria: c.nom,
          });
      });

      this.subcatRowsFiltrats = this.subcatRows;
      this.loading = false;
    },

    async createSubcategoria() {
      try {
        this.loading = true;
        this.dialogCreateSubcategory = false;
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
        this.categoria = [];
        this.getSubcategories();
      }
    },

    async updateSubcategoria() {
      try {
        this.loading = true;
        this.dialogEditSubcategory = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/update/subcategories",
          {
            id_subcategoria: this.subcategoriaEdit.id_subcategoria,
            nom: this.subcategoriaEdit.nom,
            id_categoria: this.categoria.value,
          }
        );
        const sendJson = await sendAxios.data;

        console.log("UpdateSubcategoria", sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getSubcategories();
      }
    },

    async deleteSubcategoria() {
      try {
        this.loading = true;
        this.dialogDeleteSubcategory = false;
        const sendAxios = await axios.post(
          process.env.CRIDADA_API + "api/delete/subcategories",
          {
            id_subcategoria: this.subcategoriaDelete.id_subcategoria,
          }
        );
        const sendJson = await sendAxios.data;
        console.log(sendJson);
      } catch ($a) {
        console.log($a);
      } finally {
        this.loading = false;
        this.getSubcategories();
      }
    },

    showCreateSubcategory() {
      this.dialogCreateSubcategory = true;
    },

    showEditSubcategoryDialog(props) {
      this.dialogEditSubcategory = true;
      this.subcategoriaEdit.id_subcategoria = props.row.id_subcategoria;
      this.subcategoriaEdit.nom = props.row.subcategoria;
    },

    showDeleteSubcategoryDialog(props) {
      this.dialogDeleteSubcategory = true;
      this.subcategoriaDelete.id_subcategoria = props.row.id_subcategoria;
      this.subcategoriaDelete.nom = props.row.subcategoria;
    },
  },
  mounted() {
    this.getCategories();
    this.getSubcategories();
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
