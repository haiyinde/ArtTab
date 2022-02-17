<template>
  <div>
    <nav-bar />
    <div class="container">
      <search-title></search-title>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavBar from "@/components/layout/NavBar.vue";
import SearchTitle from "@/components/search/SearchTitle.vue";

export default defineComponent({
  components: {
    NavBar,
    SearchTitle
  },
});
</script>

<style lang="scss" scoped>
.container {
  margin: auto;
}
</style>
