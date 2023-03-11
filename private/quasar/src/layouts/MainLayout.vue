<template>
  <q-layout view="lHh Lpr lFf" class="shadow-2 rounded-borders">
    <q-header elevated class="bg-purple-9">
      <q-toolbar>
        <q-btn flat dense round icon="menu" aria-label="Menu" @click="toggleLeftDrawer" />

        <q-toolbar-title> PCEconomic Admin </q-toolbar-title>

        <q-btn flat dense icon="home" aria-label="Volver" class="text-center" @click="redirect"
          >Volver a la parte publica</q-btn
        >
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header>Bienvenido al apartado de gestion</q-item-label>

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
    title: "Categories",
    icon: "category",
    link: "/gestioncategories",
  },
  {
    title: "Articles",
    icon: "add_business",
    link: "/gestionproductes",
  },
  {
    title: "Correos",
    icon: "mail_outline",
    link: "/gestioncorreos",
  },
  {
    title: "Factures",
    icon: "receipt",
    link: "/gestionfacturas",
  },
];

export default defineComponent({
  name: "MainLayout",

  components: {
    EssentialLink,
    LinkPersonalitzat,
  },

  methods: {
    redirect() {
      // quiero que me redirija a pceconomic.live sin dominio
      window.location.href = "https://pceconomic.live";
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
