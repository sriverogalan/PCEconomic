<template>
  <q-layout view="lHh Lpr lFf" class="shadow-2 rounded-borders">
    <q-header elevated class="bg-purple-9">
      <q-toolbar>
        <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer" />

        <q-toolbar-title> Admin PCEconomic App </q-toolbar-title>
        
        <q-btn
          flat
          dense
          icon="brightness_4"
          aria-label="Dark mode"
          class="text-center q-pa-md"
          @click="toggleDark"
        >
          <q-tooltip> Dark mode </q-tooltip>
        </q-btn>
        <q-btn
          flat
          dense
          icon="storefront"
          aria-label="Volver"
          class="text-center q-pa-md"
          @click="redirect"
          >
          <q-tooltip> Volver a la tienda </q-tooltip>
          </q-btn
        >
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header>Bienvenido al apartado de gestión</q-item-label>

        <LinkPersonalitzat
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { useQuasar } from "quasar";
import {
  default as EssentialLink,
  default as LinkPersonalitzat,
} from "src/components/LinkPersonalitzat.vue";
import { defineComponent, ref } from "vue";

const linksList = [
  {
    title: "Usuaris",
    icon: "person",
    link: "/",
  },
  {
    title: "Marcas",
    icon: "work",
    link: "/gestionmarcas",
  },
  {
    title: "Categorias",
    icon: "category",
    link: "/gestioncategories",
  },
  {
    title: "Articulos",
    icon: "add_business",
    link: "/gestionproductes",
  },
  {
    title: "Correos",
    icon: "mail_outline",
    link: "/gestioncorreos",
  },
  {
    title: "Facturas",
    icon: "receipt",
    link: "/gestionfacturas",
  },
];

const $q = useQuasar();

export default defineComponent({
  name: "MainLayout",

  components: {
    EssentialLink,
    LinkPersonalitzat,
  },

  data() {
    return {
      dark: false,
    };
  },

  methods: {
    redirect() {
      window.location.href = "https://pceconomic.live";
    },
    toggleDark() {
      console.log("hola");
      this.dark = !this.dark;
      this.$q.dark.set(this.dark);
    },
  },

  setup() {
    const leftDrawerOpen = ref(false);

    return {
      essentialLinks: linksList,
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
    };
  },
});
</script>
