<template>
  <article class="container">
    <div class="title">마이 갤러리<palette style="font-size: 1.8rem;"></palette></div>
    <router-link
      v-if="userInfo.id == profileInfo.id"
      class="decorate-gallery-btn"
      :to="{ name: 'DecorateGallery' }"
      >꾸미기</router-link
    >
    <div class="gallery-box">
      <div id="mygallery">
        <added-artwork-item
          v-for="(artwork, index) in addedArtworkList"
          :key="artwork.artworkId"
          :artwork="artwork"
          :index="index"
          :resizable="false"
          :draggable="false"
        >
        </added-artwork-item>
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AddedArtworkItem from "@/components/profile/child/AddedArtworkItem.vue";
import { mapState, mapActions } from "vuex";
import { Palette } from "mdue";

const accountsStore = "accountsStore";
const galleryStore = "galleryStore";

export default defineComponent({
  name: "Gallery",
  components: { AddedArtworkItem, Palette },
  data() {
    return {
      profileInfo: {
        id: this.$route.params.id as unknown as number,
      },
    };
  },
  mounted() {
    this.getArtworkList(this.userInfo.nickname);
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
    ...mapState(galleryStore, ["addedArtworkList"]),
  },
  methods: {
    ...mapActions(galleryStore, ["getArtworkList"]),
    // getGalleryImage() {
    //   // Image API
    //   return this.galleryImage
  },
});
</script>

<style lang="scss" scoped>
.container {
  max-width: 1200px;
  min-width: 320px;
  margin: 0 auto;
  position: relative;
}

.title {
  display: flex;
  align-items: center;
  margin-top: $size-large;
  margin-left: $size-large;
  font-size: $size-large;
  font-weight: $weight-semi-bold;
}

.gallery-box {
  width: 100%;
  border: 1px solid $grey;
  overflow-x: auto;
  margin-top: $size-medium;
}

#mygallery {
  position: relative;
  width: 740px;
  height: 400px;
  margin: auto;

  background: url("../../../src/assets/images/gallerybackground.jpg") no-repeat
    center center;
  background-size: contain;
}

.decorate-gallery-btn {
  font-size: $font-regular;
  font-weight: $weight-semi-bold;
  line-height: 1.8;
  border: 1.5px solid $black;
  border-radius: 0.3rem;
  // 위치
  position: absolute;
  top: 0;
  right: 0;
  // margin-right: 1rem;
  // width: 4rem;
  width: 120px;
  text-align: center;
  align-items: center;
  justify-content: center;
  // hover시 색상 전환 천천히
  transition: border-color 0.5s, background-color 0.5s, color 0.5s;
  // hover시 색상 전환
  &:hover {
    background-color: $black;
    color: $white;
    border-color: transparent;
  }
}
</style>
