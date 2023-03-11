const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        name: "Token",
        component: () => import("pages/IndexPage.vue"),
      },
      {
        path: "gestionmarcas",
        name: "PCEconomic Marcas",
        component: () => import("pages/GestionMarcas.vue"),
      },
      {
        path: "gestioncategories",
        name: "PCEconomic Categories",
        component: () => import("pages/GestionCategorias.vue"),
      },
      {
        path: "gestionproductes",
        name: "PCEconomic Productos",
        component: () => import("pages/GestionProductos.vue"),
      },
      {
        path: "gestioncorreos",
        name: "PCEconomic Correos",
        component: () => import("pages/GestionCorreos.vue"),
      },
      {
        path: "gestionfacturas",
        name: "PCEconomic Facturas",
        component: () => import("pages/GestionFacturas.vue"),
      },
      {
        path: "article/:id_article/:nom",
        name: "PCEconomic Propietats",
        component: () => import("pages/GestionPropietats.vue"),
      },
    ],
  },

  {
    path: "/error/:error",
    name: "Error",
    component: () => import("pages/ErrorPage.vue"),
  },
];

export default routes;
