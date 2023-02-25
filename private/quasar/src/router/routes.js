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
        name: "Index",
        component: () => import("pages/GestionMarcas.vue"),
      },
      {
        path: "gestioncategorias",
        name: "Categorias",
        component: () => import("pages/GestionCategorias.vue"),
      },
    ],
  },

  {
    path: "/error/:error",
    name: "Error",
    component: () => import("pages/ErrorPage.vue"),
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
