<template>
  <div>
    <div class="container">
      <masonry-wall
        :items="items"
        :ssr-columns="1"
        :column-width="300"
        :gap="16"
      >
        <template #default="{ item }">
          <div class="thumbnail-wrapper">
            <img :src="item.imageUrl" :alt="`${item.artworkTitle}`" />
            <div class="overlay">
              <div class="info">
                <router-link
                  :to="{
                    name: 'ArtworkDetail',
                    params: { id: item.artworkId },
                  }"
                >
                  <span style="color: white" class="artwork-title">{{
                    item.artworkTitle
                  }}</span>
                </router-link>
                <router-link
                  :to="{ name: 'Profile', params: { id: item.memberId } }"
                >
                  <span style="color: white" class="artwork-artist"
                    >By {{ item.memberNickname }}</span
                  >
                </router-link>
              </div>
              <div class="button-top-right">
                <!-- <like-button
                  class="icon"
                  :liked="item.likeOrNot"
                  :artworkId="item.artworkId"
                  :userId="userInfo.id"
                  @toggle="toggleLike()"
                  @message="showToastMessage"></like-button>
                <toast-message ref="toast"></toast-message> -->
              </div>
            </div>
          </div>
        </template>
      </masonry-wall>
    </div>
    <!-- Scroll To Top Button -->
    <arrow-up-bold-circle-outline
      class="arrow scroll-to-top"
      @click="scrollToTop"
    ></arrow-up-bold-circle-outline>

    <loader></loader>
    <observer @intersect="intersected"></observer>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ArtworkAPI from "@/apis/artworkAPI";
import Observer from "@/components/main/child/Observer.vue";
import LikeButton from "@/components/common/LikeButton.vue";
import ToastMessage from "@/components/common/ToastMessage.vue";
import { mapState } from "vuex";
import Loader from "@/components/main/child/Loader.vue";
import { ArrowUpBoldCircleOutline } from "mdue";

const accountsStore = "accountsStore";

export default defineComponent({
  el: "#app",
  data() {
    return {
      page: 0,
      items: [] as any,
    };
  },
  components: {
    Observer,
    // LikeButton,
    Loader,
    ArrowUpBoldCircleOutline,
  },
  methods: {
    async intersected() {
      const res = await ArtworkAPI.getArtworkList(this.page);
      this.page++;
      const items = res.data;
      this.items = [...this.items, ...items];
    },
    // 좋아요 상태 변경
    // toggleLike(id: number) {
    //   this.items.forEach((item: { artworkId: number; }) => {
    //     if (item.artworkId === id) {

    //     }
    //   });
    // },
    showToastMessage(msg: string) {
      (this.$refs["toast"] as typeof ToastMessage).showToast(msg);
    },
    scrollToTop() {
      let currentScroll = document.documentElement.scrollTop,
        int = setInterval(frame, 6);

      function frame() {
        if (0 > currentScroll) clearInterval(int);
        else {
          currentScroll = currentScroll - 12;
          document.documentElement.scrollTop = currentScroll;
        }
      }
    },
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
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

// like button
.icon {
  font-size: 1.5rem;
  color: $white;
  cursor: pointer;
  margin-left: $size-small;
  margin-right: $size-micro;
}

// scroll to top button
.scroll-to-top {
  font-size: $font-big;
  cursor: pointer;
  position: fixed;
  z-index: 1049;
  bottom: 20px;
  border-radius: 50%;
  background-color: $white;
  &:hover {
    background-color: $black;
    color: $white;
  }
}
</style>
