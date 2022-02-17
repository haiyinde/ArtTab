<template>
  <div class="favoriteArtist">
    <artist-item
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
}
</style>
