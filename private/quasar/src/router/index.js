import { route } from "quasar/wrappers";
import {
  createMemoryHistory,
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from "vue-router";
import routes from "./routes";
/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : process.env.VUE_ROUTER_MODE === "history"
    ? createWebHistory
    : createWebHashHistory;

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(
      process.env.MODE === "ssr" ? void 0 : process.env.VUE_ROUTER_BASE
    ),
  });

  Router.beforeEach(async () => {
    const url = new URL(window.location.href);
    const tokenParam = url.searchParams.get("token");
    let token = "";
    console.log(tokenParam);

    if (tokenParam) {
      localStorage.setItem("token", tokenParam);
    }

    if (localStorage.getItem("token")) {
      token = localStorage.getItem("token");
    } else {
      window.location = "http://localhost:8080/error";
    }

    if (token) {
      await fetch(`http://localhost:8000/`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.status < 399) {
            return response.json();
          } else {
            window.location = "http://localhost:8080/error";
          }
        })
        .then((data) => {
          if (data) {
            console.log(data);
          } else {
            window.location = "http://localhost:8080/error";
          }
        })
        .catch((error) => {
          console.error(error);
        });
    }
  });

  return Router;
});
