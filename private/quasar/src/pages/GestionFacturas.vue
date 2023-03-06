<template>
  <div class="col-10">
    <h1 class="col-12 text-center">Gestiona Factures</h1>
    <div class="q-pa-md">
      <q-table
        title="Marcas"
        :rows="facturaRowsFiltrats"
        :columns="facturaColumns"
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
              icon="receipt_long"
              color="amber-5"
              @click="showLineaFacturaDialog(props)"
            />
            <q-btn
              icon="picture_as_pdf"
              class="ml-2"
              color="red-14"
              @click="showGeneratePdfDialog()"
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
      </q-table>

      <q-dialog v-model="lineaFacturaDialog" full-width position="top">
        <q-card style="width: 100%">
          <q-card-section>
            <q-table
              :title="facturaInfo.title"
              :rows="lineaFacturaRowsFiltrats"
              :columns="lineaFacturaColumns"
              row-key="nom"
              loading-label="Cargando..."
            >
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
            </q-table>
          </q-card-section>

          <q-card-section class="row justify-end">
            <div>
              <p class="text-h6">
                Preu Transport: {{ facturaInfo.preu_transport }}
              </p>
              <p class="text-h6">Preu Final: {{ facturaInfo.preu_total }}</p>
            </div>
          </q-card-section>

          <q-card-section class="row justify-end">
            <q-btn
              color="red-14"
              label="Cerrar"
              @click="lineaFacturaDialog = false"
            />
          </q-card-section>
        </q-card>
      </q-dialog>
    </div>
  </div>
</template>
<script>
import process from "process";
export default {
  name: "GestionFacturas",
  data() {
    return {
      loading: false,
      facturaColumns: [
        {
          name: "id",
          required: true,
          label: "Num Factura",
          align: "center",
          field: (row) => row.id_factura,
          sortable: true,
        },
        {
          name: "date",
          required: true,
          label: "Data Factura",
          align: "center",
          field: (row) => row.data,
          sortable: true,
        },
        {
          name: "actions",
          align: "center",
          label: "Acciones",
          field: "actions",
        },
      ],
      facturaRows: [],
      facturaRowsFiltrats: [],
      lineaFacturaColumns: [
        {
          name: "quantitat",
          required: true,
          label: "Quantitat",
          align: "center",
          field: (row) => row.quantitat,
          sortable: true,
        },
        {
          name: "nomMarca",
          required: true,
          label: "Marca",
          align: "center",
          field: (row) => row.nomMarca,
          sortable: true,
        },
        {
          name: "nomArticle",
          required: true,
          label: "Nom Article",
          align: "center",
          field: (row) => row.nomArticle,
          sortable: true,
        },
        {
          name: "propietats",
          required: true,
          label: "Propietats",
          align: "center",
          field: (row) => row.propietats,
          sortable: true,
        },
        {
          name: "preuproducte",
          required: true,
          label: "Preu Producte",
          align: "center",
          field: (row) => row.preuproducte,
          sortable: true,
        },
        {
          name: "preutotal",
          required: true,
          label: "Preu Total",
          align: "center",
          field: (row) => row.preulineatotal,
          sortable: true,
        },
      ],
      lineaFacturaRows: [],
      lineaFacturaRowsFiltrats: [],
      generatePdfDialog: false,
      lineaFacturaDialog: false,
      facturaInfo: {
        id: "",
        title: "",
        preu_transport: "",
        preu_total: "",
      },
    };
  },
  methods: {
    async getFactures() {
      this.rows = [];
      this.loading = true;
      const facturesAxios = await this.$axios.get(
        process.env.CRIDADA_API + "api/get/factures"
      );
      const factures = facturesAxios.data;
      factures.forEach((factura) => {
        this.facturaRows.push({
          id_factura: factura.id_factura,
          data: this.formatDate(factura.data),
          preu_transport: factura.preu_transport,
          preu_total: factura.preu,
        });

        factura.lineafactura.forEach((lineaFactura) => {
          this.lineaFacturaRows.push({
            id_factura: factura.id_factura,
            nomMarca: lineaFactura.marca.nom,
            nomArticle: lineaFactura.nom_article,
            propietats: lineaFactura.propietats,
            quantitat: lineaFactura.quantitat,
            preuproducte: this.formatCurrency(
              parseFloat(lineaFactura.preu / lineaFactura.quantitat)
            ),
            preulineatotal: this.formatCurrency(parseFloat(lineaFactura.preu)),
          });
        });
      });

      this.facturaRowsFiltrats = this.facturaRows;
      this.lineaFacturaRowsFiltrats = this.lineaFacturaRows;
      this.loading = false;
    },
    showLineaFacturaDialog(props) {
      this.lineaFacturaDialog = true;
      let id = props.row.id_factura;

      this.facturaInfo.id = id;
      this.facturaInfo.title = "Información de la factura nº " + id;
      this.facturaInfo.preu_transport = this.formatCurrency(
        parseFloat(props.row.preu_transport)
      );
      this.facturaInfo.preu_total = this.formatCurrency(
        parseFloat(props.row.preu_total)
      );

      this.lineaFacturaRowsFiltrats = this.lineaFacturaRows.filter(
        (row) => row.id_factura === id
      );
    },
    showGeneratePdfDialog() {
      this.generatePdfDialog = true;
    },
    formatCurrency(amount) {
      return (
        amount
          .toFixed(2)
          .toString()
          .replace(".", ",")
          .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €"
      );
    },
    // metodo para poder formatear la fecha 
    formatDate(date) {
      let d = new Date(date),
        month = "" + (d.getMonth() + 1),
        day = "" + d.getDate(),
        year = d.getFullYear();

      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;

      return [day, month, year].join("/");
    },
  },
  mounted() {
    this.getFactures();
  },
};
</script>
<style>
.ml-2 {
  margin-left: 2rem;
}

.size-2 {
  font-size: 16px;
}
</style>
