<template>
  {{ userInfo.id }}
  {{ userInfo.email }}
  {{ userInfo.intro }}
  {{ userInfo.nickname }}
  <!-- <div class="container">
    <masonry-wall
      :items="artwork_list"
      :ssr-columns="1"
      :column-width="300"
      :gap="16"
    >
      <template #default="{ item }">
        <div class="thumbnail-wrapper">
            <img :src="item.image" :alt="`${item.id}`" />
            <div class="overlay">
              <div class="info">
              <router-link
                :to="{ name: 'ArtworkDetail', params: { id: item.artworkId } }"
                >
                <span style="color: white" class="artwork-title"
                  >{{ item.title }}</span
                >
              </router-link>
              <router-link
                :to="{ name: 'Profile', params: { id: item.memberId } }"
                >
                <span style="color: white" class="artwork-artist">By {{
                  item.nickname
                }}</span>
              </router-link>
              </div>
              <div class="button-top-right">
                <like-button :liked="!valid" @click="handleLike"></like-button>
              </div>
            </div>
        </div>
      </template>
    </masonry-wall>
  </div> -->
</template>

<script lang="ts">
import { defineComponent } from "vue";
// import LikeButton from "../common/LikeButton.vue";
import ArtworkAPI from "@/apis/artworkAPI";
import { mapState, mapMutations } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  data() {
    return {
      // 내 프로필 조회 정보
      account: {
        nickname: "",
        intro: "",
        password: "",
        id: this.$route.params.id as string,
      },
      // 타인 프로필 조회 정보
      profileInfo: {
        email: "",
        nickname: "",
        intro: "",
      },
      artwork_list: [] as any,
      page: 1,
      valid: true,
    };
  },
  components: {
    // LikeButton,
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    // ...mapMutations(accountsStore, ["SET_JOIN_EMAIL"]),
    // ...mapMutations(accountsStore, ["SET_IS_LOGIN"]),
    // ...mapMutations(accountsStore, ["SET_USER_INFO"]),
    handleLike() {
      this.valid = !this.valid;
    },
    async getMyArtwork() {
      let my_artworks = await ArtworkAPI.getArtworkListByMember(
        this.profileInfo.nickname
      );
      let temp = my_artworks.data;
      console.log(temp);
      const my_artwork = [];
      let size = Object.keys(temp).length;
      for (let i = 0; i < size; i++) {
        my_artwork.push({
          artworkId: temp[i].artworkId,
          title: temp[i].artworkTitle,
          memberId: temp[i].memberId,
          nickname: temp[i].memberNickname,
          image: temp[i].saveFolder,
        });
      }
      this.artwork_list = [...this.artwork_list, ...my_artwork];
    },
    handleScroll() {
      if (
        window.scrollY + window.innerHeight >=
        document.body.scrollHeight - 50
      ) {
        this.getMyArtwork();
      }
    },
    // goDetail(item: { id: number; }) {
    //   this.$router.push({ name: 'ArtworkDetail', params: {id: item.id}});
    // }
  },
  mounted() {
    this.getMyArtwork();
    window.addEventListener("scroll", this.handleScroll);
  },
});
</script>

<style scoped lang="scss">
* {
  box-sizing: border-box;
}

.thumbnail-wrapper {
  position: relative;
  width: 300px;
  margin: 0 auto;
  transition: 0.5s ease-in-out;

  & img {
    max-width: 100%;
    height: auto;
    border: 1px solid $grey;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.5);
  }

  &:hover {
    transform: translateY(-3px);
    & .button-top-right {
      visibility: visible;
    }
    & .info {
      color: white;
      visibility: visible;
      transform: translateY(0px);
    }
  }
}

.info {
  position: absolute;
  bottom: $font-small;
  left: 10px;
  visibility: hidden;
  z-index: 3;
  transition: 0.4s, ease-in-out;
}

.container {
  margin-top: 2rem;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2;
  &:hover {
    background: rgba(0, 0, 0, 0.6);
  }
}

.button-top-right {
  position: absolute;
  top: 2px;
  right: 2px;
  z-index: 3;
  visibility: hidden;
}
</style>
