<template>
  <div>
    <p>Profile page</p>
    <div class="container">
      <profile-info></profile-info>
      <gallery></gallery>
      <profile-artwork></profile-artwork>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ProfileInfo from "../components/profile/ProfileInfo.vue";
import Gallery from "../components/profile/Gallery.vue";
import ProfileArtwork from "../components/profile/ProfileArtwork.vue";
export default defineComponent({
  components : {
    ProfileInfo,
    Gallery,
    ProfileArtwork,
  }
})
</script>

<style lang="scss" scoped>
.container {
  margin:auto;
}
</style>