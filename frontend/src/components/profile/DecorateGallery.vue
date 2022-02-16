<template>
  <article class="container">
    <div class="title">갤러리 꾸미기</div>
    <button class="btn-complete">수정완료</button>
    <div id="mygallery">
      <added-artwork-item
        v-for="(artwork, index) in addedArtworkList"
        :key="artwork.artworkId"
        :artwork="artwork"
        :index="index"
      >
      </added-artwork-item>
    </div>
    <additional-artworks></additional-artworks>
  </article>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AdditionalArtworks from "@/components/profile/child/AdditionalArtworks.vue";
import AddedArtworkItem from "@/components/profile/child/AddedArtworkItem.vue";
import { mapState, mapActions } from "vuex";

const galleryStore = "galleryStore";

export default defineComponent({
  components: { AdditionalArtworks, AddedArtworkItem },
  name: "DecorateGallery",
  data() {
    return {};
  },
  computed: {
    ...mapState(galleryStore, ["addedArtworkList"]),
  },
  methods: {},
});
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 70%;
  max-width: 900px;
  margin: 2rem auto;
}

.title {
  margin-top: $size-large;
  margin-left: $size-small;
  font-size: $size-large;
  font-weight: $weight-semi-bold;
}

#mygallery {
  position: relative;
  width: 100%;
  max-height: 400px;
  margin-top: $size-medium;
  border: 1px solid $grey;
  background: url("../../../src/assets/images/gallerybackground.jpg") no-repeat
    center center;
  background-size: contain;
  box-shadow: 0 0 6px rgba(164, 204, 199, 0.9);
  &::before {
    content: "";
    display: block;
    padding-top: 80%;
  }
}

button {
  padding: $size-micro $size-medium;
  font-size: 0.8rem;
  background-color: $white;
  color: $black;
  border: 1px solid $black;
  border-radius: $size-micro;
  cursor: pointer;
  &.btn-complete {
    position: absolute;
    top: 0;
    right: 0;
  }
}

/* 모바일 크기에서 적용되는 스타일 */
@media screen and (max-width: 640px) {
  .container {
    width: 100%;
    margin: 0;
  }
}
</style>
