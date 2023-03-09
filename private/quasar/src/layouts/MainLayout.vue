<template>
  <q-layout view="lHh Lpr lFf" class="shadow-2 rounded-borders">
    <q-header elevated class="bg-purple-9">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title> PCEconomic Admin </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header>Gestiona</q-item-label>

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
