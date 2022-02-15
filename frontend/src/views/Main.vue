<template>
  <nav-bar />
  <!-- Tab menu -->
  <div class="tab">
    <input type="radio" name="tabmenu" id="feed" checked />
    <label for="feed" class="tab-item">Feed</label>
    <input type="radio" name="tabmenu" id="FavouriteArtwork" />
    <label for="FavouriteArtwork" class="tab-item">❤ Artwork</label>
    <input type="radio" name="tabmenu" id="FavouriteArtist" />
    <label for="FavouriteArtist" class="tab-item">❤ Artist</label>

    <div class="conbox con1">
      <feed></feed>
    </div>
    <div class="conbox con2">
      <favourite-artwork></favourite-artwork>
    </div>
    <div class="conbox con3">
      <!-- 철연님 코드 -->
      <favourite-artist></favourite-artist>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavBar from "@/components/layout/NavBar.vue";
import Feed from "../components/main/Feed.vue";
import FavouriteArtwork from "../components/main/FavouriteArtwork.vue";
import FavouriteArtist from "../components/main/FavouriteArtist.vue";

export default defineComponent({
  components: {
    NavBar,
    Feed,
    FavouriteArtwork,
    FavouriteArtist,
  },
  data() {
    return {
      items: [
        { content: "그림1", height: 400 },
        { content: "그림2", height: 300 },
        { content: "그림3", height: 150 },
        { content: "그림4", height: 450 },
      ],
    };
  },
  methods: {},
});
</script>

<style scoped lang="scss">
input[type="radio"] {
  display: none;
}
input[type="radio"] + label {
  display: inline-block;
  padding: 16px;
  color: $dark-grey;
  font: size 4rem;
  cursor: pointer;
}
input[type="radio"]:checked + label {
  color: #000;
  border-bottom: 3px solid #000;
}

// 컨텐츠는 나중에 라우터로 처리
.conbox {
  margin: 0 auto;
  display: none;
}
input[id="feed"]:checked ~ .con1 {
  display: block;
}
input[id="FavouriteArtwork"]:checked ~ .con2 {
  display: block;
}
input[id="FavouriteArtist"]:checked ~ .con3 {
  display: block;
}

.tab {
  margin: 1rem;
}
</style>
