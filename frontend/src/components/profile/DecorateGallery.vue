<template>
  <article class="container">
    <div class="title">갤러리 꾸미기</div>
    <button class="btn-complete" @click="storeArtworkList">수정완료</button>
    <div class="gallery-box">
      <div id="mygallery">
        <added-artwork-item
          v-for="(artwork, index) in addedArtworkList"
          :key="artwork.artworkId"
          :artwork="artwork"
          :index="index"
        >
        </added-artwork-item>
      </div>
    </div>
    <additional-artworks></additional-artworks>
  </article>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AdditionalArtworks from "@/components/profile/child/AdditionalArtworks.vue";
import AddedArtworkItem from "@/components/profile/child/AddedArtworkItem.vue";
import GalleryAPI from "@/apis/galleryAPI";
import ResponseData from "@/types/ResponseData";
import { mapState, mapActions } from "vuex";

const accountsStore = "accountsStore";
const galleryStore = "galleryStore";

export default defineComponent({
  components: { AdditionalArtworks, AddedArtworkItem },
  name: "DecorateGallery",
  data() {
    return {};
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
    storeArtworkList() {
      GalleryAPI.setArtworkList(
        this.userInfo.nickname,
        this.addedArtworkList
      ).then((res: ResponseData) => {
        console.log(res);
        this.$router.push({
          name: "Profile",
          params: { id: this.userInfo.id },
        });
      });
    },
  },
});
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 740px;
  max-width: 900px;
  margin: 2rem auto;
}

.title {
  margin-top: $size-large;
  margin-left: $size-small;
  font-size: $size-large;
  font-weight: $weight-semi-bold;
}

.gallery-box {
  overflow-x: auto;
}

#mygallery {
  position: relative;
  width: 740px;
  height: 400px;
  margin-top: $size-medium;
  border: 1px solid $grey;
  background: url("../../../src/assets/images/gallerybackground.jpg") no-repeat
    center center;
  background-size: contain;
  box-shadow: 0 0 6px rgba(164, 204, 199, 0.9);
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
