<template>
  <div class="col-10">
    <h1 class="col-12 text-center">Gestiona Factures</h1>
    <div class="q-pa-md">
      <q-table
        :rows="facturaRowsFiltrats"
        :columns="facturaColumns"
        row-key="nomClient"
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
            @click="getFactures()"
          >
          </q-btn>
        </template>
      </q-table>

      <q-dialog v-model="lineaFacturaDialog" full-width position="top">
        <q-card style="width: 100%">
          <q-card-section>
            <q-table
              id="miTabla"
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
              icon="close"
              @click="lineaFacturaDialog = false"
            />

            <q-btn
              color="green-14"
              icon="picture_as_pdf"
              class="ml-2"
              @click="showGeneratePdfDialog()"
            />
          </q-card-section>
        </q-card>
      </q-dialog>

      <q-dialog v-model="generatePdfDialog" persistent id="generatePdfDialog">
        <q-card class="sizeTitleCard">
          <q-card-section class="row items-center">
            <div class="text-h6">Generar PDF</div>
          </q-card-section>

          <q-card-actions align="right">
            <q-btn
              flat
              label="Cancelar"
              color="red-14"
              @click="generatePdfDialog = false"
            />
            <q-btn
              icon="picture_as_pdf"
              color="purple-9"
              @click="generatePDF()"
            />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </div>
</template>
<script>
import process from "process";
import { exportToPDF } from "src/other/generatepdf.js";
import { formatCurrency, formatDate } from "src/other/utils.js";

export default {
  name: "GestionFacturas",
  data() {
    return {
      loading: false,
      filter: "",
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
          name: "nomClient",
          required: true,
          label: "Nom",
          align: "center",
          field: (row) => row.nomClient,
          sortable: true,
        },
        {
          name: "dni",
          required: true,
          label: "DNI",
          align: "center",
          field: (row) => row.dni,
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
          label: "Cantidad",
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
          label: "Nombre del Articulo",
          align: "center",
          field: (row) => row.nomArticle,
          sortable: true,
        },
        {
          name: "propietats",
          required: true,
          label: "Propiedades",
          align: "center",
          field: (row) => row.propietats,
          sortable: true,
        },
        {
          name: "preuproducte",
          required: true,
          label: "Precio Unitario",
          align: "center",
          field: (row) => row.preuproducte,
          sortable: true,
        },
        {
          name: "preutotal",
          required: true,
          label: "Precio Total",
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
      information: {
        id: "",
        date: "",
        name: "",
        direction: "",
        metodo_pago: "",
        status: "",
        transport: "",
        price: "",
      },
    };
  },
  methods: {
    filtrar() {
      this.facturaRowsFiltrats = this.facturaRows.filter((factura) => {
        return (
          factura.dni.toLowerCase().includes(this.filter.toLowerCase()) ||
          factura.nomClient.toLowerCase().includes(this.filter.toLowerCase())
        );
      });
    },
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
          data: formatDate(factura.data),
          nomClient: factura.persona.nom + " " + factura.persona.cognom1,
          dni: factura.persona.dni,
          direction: factura.direccio,
          estat: factura.estat,
          metodo_pago: factura.metodo_pagament,
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
            preuproducte: formatCurrency(
              parseFloat(lineaFactura.preu / lineaFactura.quantitat)
            ),
            preulineatotal: formatCurrency(parseFloat(lineaFactura.preu)),
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
      this.facturaInfo.preu_transport = formatCurrency(
        parseFloat(props.row.preu_transport)
      );
      this.facturaInfo.preu_total = formatCurrency(
        parseFloat(props.row.preu_total)
      );

      this.lineaFacturaRowsFiltrats = this.lineaFacturaRows.filter(
        (row) => row.id_factura === id
      );

      this.information.id = id;
      this.information.date = props.row.data;
      this.information.name = props.row.nomClient;
      this.information.direction = props.row.direction;
      this.information.metodo_pago = props.row.metodo_pago;
      this.information.status = props.row.estat;
      this.information.transport = formatCurrency(
        parseFloat(props.row.preu_transport)
      );
      this.information.price = formatCurrency(parseFloat(props.row.preu_total));
    },
    showGeneratePdfDialog() {
      this.lineaFacturaDialog = false;
      this.generatePdfDialog = true;
    },

    generatePDF() {
      exportToPDF(this.lineaFacturaRowsFiltrats, this.information);
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
