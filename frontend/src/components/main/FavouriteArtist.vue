<template>
  <div class="favoriteArtist">
    <div v-if="!artworkInfo" class="desc">
      😊 관심있는 작가를 팔로우 해보세요!
    </div>
    <artist-item
      v-else
      v-for="artwork in artworkInfo"
      :key="artwork.memberMail"
      :artwork="artwork"
    ></artist-item>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ArtistItem from "@/components/main/child/ArtistItem.vue";
import FollowArtworkInfo from "@/types/FollowArtworkInfo";
import ArtworkAPI from "@/apis/artworkAPI";
import { mapState } from "vuex";
import ResponseData from "@/types/ResponseData";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "FavouriteArtist",
  components: {
    ArtistItem,
  },
  data() {
    return {
      artworkInfo: [] as FollowArtworkInfo[],
    };
  },
  mounted() {
    this.getArtworkList();
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    getArtworkList() {
      ArtworkAPI.getFollowArtworkList(this.userInfo.nickname).then(
        (res: ResponseData) => {
          console.log(res.data);
          this.artworkInfo = res.data;
        }
      );
    },
  },
});
</script>

<style lang="scss" scoped>
.favoriteArtist {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  .desc {
    font-size: $size-large;
    margin-top: $size-big;
  }
}
</style>
