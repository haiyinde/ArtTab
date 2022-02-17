import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import MasonryWall from "@yeger/vue-masonry-wall";
import Vue3DraggableResizable from "vue3-draggable-resizable";
import VueCookies from "vue3-cookies";
import "animate.css";
import "vue3-draggable-resizable/dist/Vue3DraggableResizable.css";

createApp(App)
  .use(store)
  .use(router)
  .use(MasonryWall)
  .use(Vue3DraggableResizable)
  .use(VueCookies)
  .mount("#app");
