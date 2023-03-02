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
            <q-btn class="mb-1" color="purple-14" icon="add" @click="showCreateDialog()">
            </q-btn>
          </template>
        </q-table>

        <q-dialog v-model="dialogCreate" persistent id="dialogCreate">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Crear article</div>
            </q-card-section>

            <q-card-section>
              <q-form>
                <q-input label="Nombre" v-model="nom_article" filled class="q-mb-md" />
                <q-input
                  label="Descripcio"
                  v-model="descripcio_article"
                  filled
                  class="q-mb-md"
                />
                <q-input label="Pes (Kg)" v-model="pes_article" filled class="q-mb-md" />
                <q-select :options="marques" label="Marca" filled class="q-mb-md">
                </q-select>
              </q-form>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn flat label="Cancelar" color="red-14" @click="dialogCreate = false" />
              <q-btn label="Crear" color="purple-14" @click="createarticle()" />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog v-model="dialogEdit" persistent id="dialogUpdate">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Editar article</div>
            </q-card-section>

            <q-card-section>
              <q-form>
                <q-input
                  v-model="articleEdit.id_article"
                  label="Id"
                  filled
                  class="q-mb-md"
                  disable
                />
                <q-input
                  v-model="articleEdit.nom"
                  label="Nombre"
                  filled
                  class="q-mb-md"
                />
                <q-input
                  v-model="articleEdit.descripcio"
                  label="Descripcio"
                  filled
                  class="q-mb-md"
                />
                <q-input v-model="articleEdit.pes" label="Pes" filled class="q-mb-md" />
                <q-input
                  v-model="articleEdit.marca.id_marca"
                  label="Id Marca"
                  filled
                  class="q-mb-md"
                />

                <q-select
                  v-model="articleEdit.marca.nom"
                  :options="marques"
                  label="Marca"
                  filled
                  class="q-mb-md"
                >
                </q-select>
              </q-form>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn flat label="Cancelar" color="red-14" @click="dialogEdit = false" />
              <q-btn label="Guardar" color="purple-14" @click="updatearticle()" />
            </q-card-actions>
          </q-card>
        </q-dialog>

        <q-dialog v-model="dialogDelete" persistent id="dialogDelete">
          <q-card class="sizeTitleCard">
            <q-card-section class="row items-center">
              <div class="text-h6">Eliminar article</div>
            </q-card-section>

            <q-card-section>
              <p>Estas seguro que quieres eliminar {{ articleDelete.nom }} ?</p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn flat label="Cancelar" color="red-14" @click="dialogDelete = false" />
              <q-btn label="Eliminar" color="purple-9" @click="deletearticle()" />
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

const source = axios.CancelToken.source();

export default defineComponent({
  name: "IndexPage",
  data() {
    return {
      nom_article: "",
      descripcio_article: "",
      pes_article: "",
      filter: "",
      dialogCreate: false,
      dialogEdit: false,
      dialogDelete: false,
      loading: true,
      articleEdit: {
        id_article: "",
        nom: "",
        descripcio: "",
        pes: "",
        marca: {
          id_marca: "",
          nom: "",
        },
      },
      marques: [],
      articleDelete: {
        id_article: "",
        nom: "",
      },
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
          m.descripcio.toLowerCase().includes(this.filter.toLowerCase())
        );
      });
    },
    async getArticle() {
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
            id_marca: a.marca.id_marca,
            nom: a.marca.nom,
          },
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

    showEditDialog(props) {
      this.dialogEdit = true;
      this.articleEdit.id_article = props.row.id_article;
      this.articleEdit.nom = props.row.nom;
      this.articleEdit.descripcio = props.row.descripcio;
      this.articleEdit.pes = props.row.pes;
      this.articleEdit.marca.id_marca = props.row.marca.id_marca;
      this.articleEdit.marca.nom = props.row.marca.nom;
    },
    showDeleteDialog(props) {
      this.dialogDelete = true;
      this.articleDelete.id_article = props.row.id_article;
      this.articleDelete.nom = props.row.nom;
    },
    showCreateDialog() {
      this.dialogCreate = true;
    },
    async createarticle() {},
    async updatearticle() {},
    async deletearticle() {},
  },
  created() {
    this.getMarques();
    this.getArticle();
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
