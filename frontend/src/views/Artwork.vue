<template>
  <nav-bar />
  <div id="artwork">
    <router-view />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavBar from "@/components/layout/NavBar.vue";

export default defineComponent({
  name: "Artwork",
  components: { NavBar },
});
</script>

<style lang="scss" scoped></style>
