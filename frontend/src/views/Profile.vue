<template>
  <nav-bar />
  <div>
    <div class="container">
      <profile-info></profile-info>
      <gallery></gallery>
      <profile-artwork></profile-artwork>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavBar from "@/components/layout/NavBar.vue";
import ProfileInfo from "../components/profile/ProfileInfo.vue";
import Gallery from "../components/profile/Gallery.vue";
import ProfileArtwork from "../components/profile/ProfileArtwork.vue";
export default defineComponent({
  components: {
    NavBar,
    ProfileInfo,
    Gallery,
    ProfileArtwork,
  },
});
</script>

<style lang="scss" scoped>
.container {
  margin: auto;
}
</style>
