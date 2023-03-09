<template>
  <q-page class="row justify-center">
    <div class="col-10">
      <h1 class="col-12 text-center">Propietats {{ articleNom }}</h1>

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

          <template v-slot:body-cell-imatges="props">
            <q-td>
              <div
                style="
                  text-align: center;
                  width: 100%;
                  margin-bottom: 5px;
                  margin-top: 5px;
                "
              >
                <q-img
                  v-for="img in props.row.imgPrincipal"
                  :src="
                    fotosUrl +
                    '/img/productes/' +
                    articleId +
                    '/' +
                    props.row.id_propietats +
                    '/' +
                    img.path
                  "
                />
              </div>
            </q-td>
          </template>

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
              <q-form @submit="pushPropietats()" class="q-gutter-md d-flex">
                <q-toggle
                  v-model="articlesSubcategories.es_principal"
                  label="Aquesta es la propietat principal?"
                  color="purple-14"
                  left-label
                />
                <q-file
                  name="poster_file"
                  v-model="file"
                  filled
                  label="Elegeix la foto principal"
                  accept=".jpg"
                >
                  <template v-slot:prepend>
                    <q-icon name="upload">
                      <q-tooltip anchor="top middle">
                        Nomes se admet el format JPG
                      </q-tooltip>
                    </q-icon>
                  </template>
                </q-file>

                <q-file
                  name="cover_files"
                  v-model="files"
                  filled
                  multiple
                  use-chips
                  label="Elegeix les fotos secundaries"
                  accept=".jpg"
                >
                  <template v-slot:prepend>
                    <q-icon name="cloud_upload">
                      <q-tooltip anchor="top middle">
                        INFORMACIO : Per ordenar les imatges secundaries, les has de pujar
                        amb el numero de l'ordre que li vulguis posar. Com per exemple :
                        1.jpg, 2.jpg, 3.jpg...
                      </q-tooltip>
                    </q-icon>
                  </template>
                </q-file>

                <div class="row justify-between">
                  <q-input
                    v-model="articlesSubcategories.preu"
                    label="Preu (€)"
                    filled
                    class="col-5"
                  />

                  <q-input
                    v-model="articlesSubcategories.stock"
                    label="Stock (ud.)"
                    filled
                    type="number"
                    class="col-6"
                  />
                </div>

                <q-select
                  v-model="articlePropietats.propietats"
                  :options="propietats"
                  use-input
                  use-chips
                  multiple
                  input-debounce="0"
                  rules=" val => val.length > 0 || 'Selecciona almenys una propietat' "
                  @new-value="createProps"
                  label="Elegeix les seves propietats"
                  @update:model-value="(val) => (articlePropietats.propietats = val)"
                  filled
                ></q-select>
                <div>
                  <q-select
                    v-for="props in articlePropietats.propietats"
                    v-model="articlePropietats.propietats_valors[props]"
                    :options="valors"
                    use-input
                    use-chips
                    multiple
                    input-debounce="0"
                    @new-value="createValue"
                    :label="props"
                    filled
                    class="mt-1"
                  >
                  </q-select>
                </div>

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
              <p>
                Estas seguro que quieres eliminar {{ articlePropietats.id_propietats }} ?
              </p>
            </q-card-section>

            <q-card-actions align="right">
              <q-btn flat label="Cancelar" color="red-14" @click="dialogDelete = false" />
              <q-btn label="Eliminar" color="purple-9" @click="deletePropietats()" />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </div>
    </div>

    <q-dialog v-model="mensajeServidor" persistent>
      <q-card style="min-width: 500px">
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
import { defineComponent } from "vue";

const source = axios.CancelToken.source();

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
      fotosUrl: process.env.RUTA_IMG,

      file: null,
      files: [],

      valors_propietats: [],
      propietats: [],
      valors: [],

      rows: [],
      rowsFiltrats: [],
      articlesSubcategories: [],
      subcategories: [],

      articlePropietats: {
        id_propietats: "",
        es_principal: "",
        preu: "",
        stock: "",
        propietats: [],
        propietats_valors: {},
        paths: "",
        valors: [],
        path: [],
        imgPrincipal: "",
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
          field: (row) => (row.es_principal ? "Si" : "No"),
          sortable: true,
        },
        {
          name: "Preu",
          required: true,
          label: "Preu",
          align: "center",
          field: (row) => this.formatearEuros(row.preu),
          sortable: true,
        },
        {
          name: "Stock",
          required: true,
          label: "Stock disponible",
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
          name: "imatges",
          required: true,
          label: "Imagen principal",
          align: "center",
          field: (row) => row.imgPrincipal,
        },
        {
          name: "actions",
          align: "center",
          label: "Acciones",
          field: "actions",
        },
      ],
    };
  },
  methods: {
    createProps(val, done) {
      if (val.length > 0) {
        if (!this.propietats.includes(val)) {
          this.propietats.push(val);
        }
        done(val, "toggle");
      }
    },
    createValue(val, done) {
      if (val.length > 0) {
        if (!this.valors.includes(val)) {
          this.valors.push(val);
        }
        done(val, "toggle");
      }
    },
    addPropietat() {
      const addProps = document.querySelector("#addProps");
      // quitar hidden addProp
      addProps.classList.remove("hidden");
    },
    formatearEuros(numero) {
      return new Intl.NumberFormat("es-ES", {
        style: "currency",
        currency: "EUR",
      }).format(numero);
    },

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
      this.getValors();

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
        let propietats = "";
        let paths = "";
        a.valors.forEach((v) => {
          propietats += v.propietat[0].nom + " " + v.valor;

          if (a.valors.length > 1 && a.valors.indexOf(v) != a.valors.length - 1) {
            propietats += ", ";
          }
        });
        a.imatges.forEach((i) => {
          paths += i.path;
          if (a.imatges.length > 1 && a.imatges.indexOf(i) != a.imatges.length - 1) {
            paths += ", ";
          }
        });
        console.log("----- Img ----");
        console.log(a.imatges);
        this.rows.push({
          id_propietats: a.id_propietats,
          es_principal: a.es_principal == 1 ? true : false,
          preu: a.preu,
          stock: a.stock,
          propietats: propietats,
          valors: a.valors,
          pathImages: a.paths,
          paths: paths,
          imgPrincipal: a.imatges.filter((i) => i.principal == 1),
        });
      });

      this.rowsFiltrats = this.rows;
      this.loading = false;
    },

    async getValors() {
      const valorsAxios = await axios.get(process.env.CRIDADA_API + "api/get/valors", {
        params: {
          id_article: this.articleId,
        },
      });
      const valorsJson = await valorsAxios.data;
      console.log(valorsJson);

      valorsJson.forEach((a) => {
        this.valors_propietats.push({
          propietat: a.propietat,
          valor: {
            id_valor: a.id_valor,
            valor: a.valor,
          },
        });
        this.valors.push(a.valor);
      });
      this.valors = this.valors.filter((v, i, a) => a.indexOf(v) === i);

      const propietatAxios = await axios.get(
        process.env.CRIDADA_API + "api/get/propietat"
      );
      const propietatJson = await propietatAxios.data;

      propietatJson.forEach((a) => {
        this.propietats.push(a.nom);
      });

      this.propietats = this.propietats.filter((v, i, a) => a.indexOf(v) === i);
    },

    showEditDialog(props) {
      this.activeId = true;
      this.titolcard = "Edita la propiedad " + props.row.id_propietats;
      this.articlesSubcategories.id_propietats = props.row.id_propietats;
      this.articlesSubcategories.es_principal = props.row.es_principal;
      this.articlesSubcategories.preu = props.row.preu;
      this.articlesSubcategories.stock = props.row.stock;
      this.articlesSubcategories.propietats = props.row.propietats;
      this.articlesSubcategories.paths = props.row.paths;
      this.articlesSubcategories.valors = props.row.valors;
      this.articlesSubcategories.path = props.row.path;
      this.articlePropietats.id_propietats = props.row.id_propietats;

      this.dialogEdit = true;
    },
    showDeleteDialog(props) {
      this.activeId = true;
      this.articlePropietats.id_propietats = props.row.id_propietats;
      this.articlesSubcategories.id_propietats = props.row.id_propietats;
      this.articlesSubcategories.es_principal = props.row.es_principal;
      this.articlesSubcategories.preu = props.row.preu;
      this.articlesSubcategories.stock = props.row.stock;
      this.articlesSubcategories.propietats = props.row.propietats;
      this.articlesSubcategories.paths = props.row.paths;
      this.articlesSubcategories.valors = props.row.valors;
      this.articlesSubcategories.path = props.row.path;

      this.articlePropietats.id_propietats = props.row.id_propietats;

      this.dialogDelete = true;
    },
    showCreateDialog() {
      this.activeId = false;
      this.titolcard = "Crea tu propiedad";
      this.articlesSubcategories.id_propietats = "";
      this.articlesSubcategories.es_principal = "";
      this.articlesSubcategories.preu = "";
      this.articlesSubcategories.stock = "";
      this.articlesSubcategories.propietats = "";
      this.articlesSubcategories.paths = "";
      this.articlesSubcategories.valors = [];
      this.articlesSubcategories.path = [];
      this.articlePropietats.propietats_valors = {};
      this.articlePropietats.id_propietats = [];
      this.dialogEdit = true;
    },
    async pushPropietats() {
      let articleJson = "";
      this.dialogEdit = false;
      this.loading = true;
      let e = "";

      const articleAxios = await axios
        .post(process.env.CRIDADA_API + "api/create/propietats", {
          id_article: this.articleId,
          id_propietats: this.articlesSubcategories.id_propietats
            ? this.articlesSubcategories.id_propietats
            : null,
          es_principal: this.articlesSubcategories.es_principal ? 1 : 0,
          preu: this.articlesSubcategories.preu,
          stock: this.articlesSubcategories.stock,
          propietats_valors: this.articlePropietats.propietats_valors,
          file: this.file,
          files: this.files,
        })
        .catch(function (error) {
          e = error;
        });

      this.mensajeServidor = true;
      if (articleAxios) {
        articleJson = await articleAxios.data;
        this.message = articleJson.message;
      } else {
        this.message = e.response.data.message;
      }
      this.updateTable();
    },
    async deletePropietats() {
      let articleJson = "";
      this.dialogDelete = false;
      this.loading = true;

      const articleAxios = await axios.post(
        process.env.CRIDADA_API + "api/delete/propietats",
        {
          id_propietat: parseInt(this.articlePropietats.id_propietats),
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
.mt-1 {
  margin-top: 1rem;
}
.background {
  background-color: #b1b1b1;
}
.sizeTitleCard {
  width: 350px;
}
</style>
