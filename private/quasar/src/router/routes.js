const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: ":token",
        name: "Token",
        component: () => import("pages/IndexPage.vue"),
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
