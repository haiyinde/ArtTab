import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import MasonryWall from "@yeger/vue-masonry-wall";
import VueCookies from "vue3-cookies";
import "animate.css";

createApp(App)
  .use(store)
  .use(router)
  .use(MasonryWall)
  .use(VueCookies)
  .mount("#app");
