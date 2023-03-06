<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">{{ articleNom }}</h1>

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
            <q-btn color="purple-14" icon="add" @click="showCreateDialog()" />
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
                  type="textarea"
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
      articleId: this.$route.params.id_article,
      articleNom: this.$route.params.nom,

      articlePropietats: {
        id_propietats: "",
        es_principal: "",
        preu: "",
        stock: "",
        propietats: "",
      },

      columns: [
        {
          name: "Id",
          required: true,
          label: "Id",
          align: "center",
          field: (row) => row.id_propietats,
          sortable: true,
        },
        {
          name: "Es principal?",
          required: true,
          label: "Es principal?",
          align: "center",
          field: (row) => row.es_principal,
          sortable: true,
        },
        {
          name: "Preu",
          required: true,
          label: "Preu",
          align: "center",
          field: (row) => row.preu,
          sortable: true,
        },
        {
          name: "Stock",
          required: true,
          label: "Stock",
          align: "center",
          field: (row) => row.stock,
          sortable: true,
        },
        {
          name: "Propietats",
          required: true,
          label: "Propiedades",
          align: "center",
          field: (row) => row.propietats,
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

      const propietatsAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/propietats",
        {
          params: {
            id_article: this.articleId,
          },
        }
      );
      const propietatsJson = await propietatsAxios.data;
      console.log(propietatsJson);

      propietatsJson.forEach((a) => {
        this.rows.push({
          id_propietats: a.id_propietats,
          es_principal: a.es_principal == 1 ? "Si" : "No",
          preu: a.preu,
          stock: a.stock,
          propietats: a.propietats,
        });
      });

      this.rowsFiltrats = this.rows;
      this.loading = false;
    },
    showEditDialog(props) {
      this.activeId = true;
      this.titolcard = "Edita el articulo " + props.row.nom;

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
