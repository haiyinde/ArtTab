<template>
  <p class="title">전시할 작품</p>
  <p class="desc">내가 좋아하는 작품을 전시해보세요!</p>
  <div class="searchbar-box">
    <input
      type="text"
      class="searchbar"
      placeholder="작가 또는 작품명 입력"
      v-model="keyword"
      @keyup.enter="searchArtworks"
    />
    <magnify class="icon searchbar-icon" @click="searchArtworks"></magnify>
  </div>
  <div class="artworks-box">
    <masonry-wall
      v-if="searchedArtworkList.length > 0"
      :items="searchedArtworkList"
      :ssr-columns="1"
      :column-width="200"
      :gap="16"
    >
      <template #default="{ item }">
        <img
          :src="item.saveFolder"
          :alt="item.artworkTitle"
          @click="addArtworkToGallery(item.artworkId, item.saveFolder)"
        />
      </template>
    </masonry-wall>
    <p v-else class="artworkbox-desc">
      마음에 드는 작품에 좋아요를 누르고<br />
      나만의 갤러리를 꾸며보세요.
    </p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ArtworkAPI from "@/apis/artworkAPI";
import ResponseData from "@/types/ResponseData";
import FavoriteArtworkInfo from "@/types/FavoriteArtworkInfo";
import { Magnify } from "mdue";
import { mapState, mapActions } from "vuex";

const accountsStore = "accountsStore";
const galleryStore = "galleryStore";

export default defineComponent({
  name: "AdditionalArtworks",
  components: {
    Magnify,
  },
  data() {
    return {
      artworkList: [] as FavoriteArtworkInfo[],
      searchedArtworkList: [] as FavoriteArtworkInfo[],
      keyword: "",
    };
  },
  mounted() {
    this.getArtworkList();
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(galleryStore, ["addArtwork"]),
    // 좋아요한 작품 목록 불러오기
    getArtworkList() {
      ArtworkAPI.getLikeArtworkList(this.userInfo.nickname).then(
        (res: ResponseData) => {
          this.artworkList = res.data;
          this.searchedArtworkList = res.data;
        }
      );
    },
    // 작품을 클릭하면 갤러리에 추가
    addArtworkToGallery(id: number, imgUrl: string) {
      this.addArtwork({
        artworkId: id,
        height: 120,
        weight: 100,
        xloc: 10,
        yloc: 10,
        saveFolder: imgUrl,
      });
    },
    searchArtworks() {
      if (!this.keyword) {
        this.searchedArtworkList = this.artworkList;
        return;
      }
      this.searchedArtworkList = this.artworkList.filter(
        (artwork) =>
          artwork.artworkTitle.indexOf(this.keyword) >= 0 ||
          artwork.memberNickname.indexOf(this.keyword) >= 0
      );
    },
  },
});
</script>

<style lang="scss" scoped>
.title {
  margin: $size-large $size-small 0;
  font-size: $size-large;
  font-weight: $weight-semi-bold;
}
.desc {
  margin: $size-small;
  color: $dark-grey;
}
.searchbar-box {
  position: relative;
  display: flex;
  margin: $size-regular 0;
  align-items: center;
  width: 100%;
  .searchbar {
    width: 100%;
    padding: $size-small $size-regular;
    border: 1px solid $grey;
    font-size: $font-small;
  }
  .searchbar-icon {
    position: absolute;
    right: $size-regular;
    color: $dark-grey;
    font-size: 1.5rem;
    cursor: pointer;
  }
}
.artworks-box {
  width: 100%;
  max-height: 400px;
  border: 1px solid $grey;
  overflow-y: auto;
  padding: $size-small;
  text-align: center;
  .artworkbox-desc {
    line-height: $size-large;
  }
  img {
    width: 12rem;
    cursor: pointer;
    box-shadow: rgba(50, 50, 93, 0.25) 0px 6px 12px -2px,
      rgba(0, 0, 0, 0.3) 0px 3px 7px -3px;
  }
}
</style>
