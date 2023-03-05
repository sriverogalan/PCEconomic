<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Gestiona els Articles</h1>

      <div class="q-pa-md">
        <q-table
          title="articles"
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
                icon="design_services"
                color="purple-14"
                @click="showPropietats(props)"
              >
                <q-tooltip> Propietats {{ props.row.nom }} </q-tooltip>
              </q-btn>
              <q-btn
                icon="edit"
                class="ml-2"
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
              color="purple-14"
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
              color="purple-14"
              icon="add"
              @click="showCreateDialog()"
            />
            <q-btn
              class="mb-1 ml-1"
              color="amber-14"
              icon="refresh"
              @click="updateTable()"
            />
          </template>
        </q-table>

        <q-dialog v-model="dialogEdit" persistent id="dialogUpdate">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">{{ titolcard }}</div>
            </q-card-section>

            <q-card-section>
              <q-form @submit="pushArticle()" class="q-gutter-md">
                <q-input
                  v-show="activeId"
                  v-model="articleEdit.id_article"
                  label="Id"
                  filled
                  class="q-mb-md"
                  disable
                  lazy-rules="
                    val => {
                      return val.length > 0 || 'El id es obligatorio';
                    }
                  "
                />
                <q-input
                  v-model="articleEdit.nom"
                  label="Nombre"
                  filled
                  class="q-mb-md"
                  lazy-rules="
                    val => {
                      return val.length > 0 || 'El id es obligatorio';
                    }
                  "
                />
                <q-input
                  v-model="articleEdit.descripcio"
                  label="Descripcio"
                  filled
                  class="q-mb-md"
                  lazy-rules="
                    val => {
                      return val.length > 0 || 'El id es obligatorio';
                    }
                  "
                />
                <q-input v-model="articleEdit.pes" label="Pes" filled class="q-mb-md" />
                <q-select
                  v-model="articleEdit.marca.nom"
                  :options="marques"
                  label="Marca"
                  filled
                  class="q-mb-md"
                />

                <q-select
                  v-model="articleEdit.subcategoria.nom"
                  :options="
                    subcategories.map((s) => {
                      return s.nom;
                    })
                  "
                  label="Subcategoria"
                  filled
                  class="q-mb-md"
                />
                <q-card-actions align="right">
                  <q-btn
                    flat
                    label="Cancelar"
                    color="red-14"
                    @click="dialogEdit = false"
                  />
                  <q-btn type="submit" label="Guardar" color="purple-14" />
                </q-card-actions>
              </q-form>
            </q-card-section>
          </q-card>
        </q-dialog>

        <q-dialog v-model="dialogDelete" persistent id="dialogDelete">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Eliminar article</div>
            </q-card-section>

            <q-card-section>
              <p>Estas seguro que quieres eliminar {{ articleEdit.nom }} ?</p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn flat label="Cancelar" color="red-14" @click="dialogDelete = false" />
              <q-btn label="Eliminar" color="purple-9" @click="deletearticle()" />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </div>
    </div>

    <q-dialog v-model="mensajeServidor" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">{{ message }}</div>
        </q-card-section>
        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Cerrar" color="red-14" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script>
import axios from "axios";
import process from "process";
import { useQuasar } from "quasar";
import { defineComponent } from "vue";

const source = axios.CancelToken.source();
const $q = useQuasar();

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      titolcard: "",
      activeId: false,
      filter: "",
      dialogEdit: false,
      dialogDelete: false,
      loading: true,
      mensajeServidor: false,
      message: "",
      articleEdit: {
        id_article: "",
        nom: "",
        descripcio: "",
        pes: "",
        marca: {
          id_marca: "",
          nom: "",
        },
        subcategoria: {
          id_subcategoria: "",
          nom: "",
        },
      },
      marques: [],
      columns: [
        {
          name: "Id",
          required: true,
          label: "Id",
          align: "center",
          field: (row) => row.id_article,
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
          name: "Descripcio",
          required: true,
          label: "Descripcio",
          align: "center",
          field: (row) => row.descripcio,
          sortable: true,
        },
        {
          name: "Pes",
          required: true,
          label: "Pes (Kg)",
          align: "center",
          field: (row) => row.pes,
          sortable: true,
        },
        {
          name: "Marca",
          required: true,
          label: "Marca",
          align: "center",
          field: (row) => row.marca.nom,
          sortable: true,
        },
        {
          name: "Subcategoria",
          required: true,
          label: "Subcategoria",
          align: "center",
          field: (row) => row.subcategoria.nom,
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
      articlesSubcategories: [],
      subcategories: [],
    };
  },
  methods: {
    filtrar() {
      this.rowsFiltrats = this.rows.filter((m) => {
        return (
          m.nom.toLowerCase().includes(this.filter.toLowerCase()) ||
          m.descripcio.toLowerCase().includes(this.filter.toLowerCase())
        );
      });
    },
    async updateTable() {
      this.dialogEdit = false;
      this.loading = true;
      this.rows = [];
      const articleAxios = await axios.get(process.env.CRIDADA_API + "api/get/articles", {
        cancelToken: source.token,
      });
      const articleJson = await articleAxios.data;
      console.log(articleJson);

      articleJson.forEach((a) => {
        this.rows.push({
          id_article: a.id_article,
          nom: a.nom,
          descripcio: a.descripcio,
          pes: a.pes,
          marca: {
            id_marca: a.id_marca,
            nom: a.marca.nom,
          },
          subcategoria: {
            id_subcategoria: "",
            nom: "",
          },
        });
      });

      this.rows.forEach((a) => {
        this.articlesSubcategories.forEach((aS) => {
          if (a.id_article == aS.id_article) {
            a.subcategoria.id_subcategoria = aS.id_subcategoria;
          }
        });
      });

      this.rows.forEach((a) => {
        this.subcategories.forEach((s) => {
          if (a.subcategoria.id_subcategoria == s.id_subcategoria) {
            a.subcategoria.nom = s.nom;
          }
        });
      });

      this.rowsFiltrats = this.rows;
      this.loading = false;
    },
    async getMarques() {
      const marquesAxios = await axios.get(process.env.CRIDADA_API + "api/get/marques", {
        cancelToken: source.token,
      });
      const marquesJson = await marquesAxios.data;
      console.log(marquesJson);

      marquesJson.forEach((m) => {
        if (m.is_actiu) {
          this.marques.push(m.nom);
        }
      });
    },
    async getArticlesSubcategories() {
      const articlesSubcategoriesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/articlessubcategories"
      );
      const articlesSubcategoriesJson = await articlesSubcategoriesAxios.data;
      articlesSubcategoriesJson.forEach((aS) => {
        this.articlesSubcategories.push({
          id_article: aS.id_article,
          id_subcategoria: aS.id_subcategoria,
        });
      });
    },
    async getSubcategories() {
      const subcategoriesAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/subcategories"
      );
      const subcategoriesJson = await subcategoriesAxios.data;

      subcategoriesJson.forEach((s) => {
        this.subcategories.push({
          id_subcategoria: s.id_subcategoria,
          nom: s.nom,
        });
      });
    },
    showEditDialog(props) {
      this.activeId = true;
      this.titolcard = "Edita el articulo " + props.row.nom;
      this.articleEdit.id_article = props.row.id_article;
      this.articleEdit.nom = props.row.nom;
      this.articleEdit.descripcio = props.row.descripcio;
      this.articleEdit.pes = props.row.pes;
      this.articleEdit.marca.id_marca = props.row.marca.id_marca;
      this.articleEdit.marca.nom = props.row.marca.nom;
      this.articleEdit.subcategoria.id_subcategoria =
        props.row.subcategoria.id_subcategoria;
      this.articleEdit.subcategoria.nom = props.row.subcategoria.nom;

      this.dialogEdit = true;
    },
    showDeleteDialog(props) {
      this.articleEdit.id_article = props.row.id_article;
      this.articleEdit.nom = props.row.nom;
      this.dialogDelete = true;
    },
    showCreateDialog() {
      this.activeId = false;
      this.titolcard = "Crea tu articulo";
      this.articleEdit.id_article = "";
      this.articleEdit.nom = "";
      this.articleEdit.descripcio = "";
      this.articleEdit.pes = "";
      this.articleEdit.marca.id_marca = "";
      this.articleEdit.marca.nom = "";
      this.articleEdit.subcategoria.id_subcategoria = "";
      this.articleEdit.subcategoria.nom = "";
      this.dialogEdit = true;
    },
    async pushArticle() {
      let articleJson = "";
      this.dialogEdit = false;
      this.loading = true;
      const articleAxios = await axios.post(
        process.env.CRIDADA_API + "api/create/articles",
        {
          id_article: this.articleEdit.id_article,
          nom: this.articleEdit.nom,
          descripcio: this.articleEdit.descripcio,
          pes: this.articleEdit.pes,
          marca: this.articleEdit.marca.nom,
          subcategoria: this.articleEdit.subcategoria.nom,
        }
      );
      articleJson = await articleAxios.data;
      this.mensajeServidor = true;
      this.message = articleJson.message;
      this.updateTable();
    },
    async deletearticle() {
      let articleJson = "";
      this.dialogDelete = false;
      this.loading = true;
      const articleAxios = await axios.post(
        process.env.CRIDADA_API + "api/delete/articles",
        {
          id_article: this.articleEdit.id_article,
        }
      );
      articleJson = await articleAxios.data;
      this.mensajeServidor = true;
      this.message = articleJson.message;
      this.updateTable();
    },
  },
  async created() {
    await this.getArticlesSubcategories();
    await this.getSubcategories();
    await this.getMarques();
    await this.updateTable();
  },
});
</script>

<style scoped>
.ml-1 {
  margin-left: 1rem;
}
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
