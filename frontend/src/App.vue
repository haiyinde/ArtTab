<template>
  <nav-bar />
  <router-view />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavBar from "@/components/layout/NavBar.vue";

export default defineComponent({
  components: { NavBar },
});
</script>

<style lang="scss">
/* font */
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Crimson+Text:wght@600&family=Noto+Sans+KR:wght@300;400;500;700&display=swap");
</style>
