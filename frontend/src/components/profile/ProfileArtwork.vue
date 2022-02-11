<template>
  <!-- <div class="container"> -->
  <div class="container">
    <div class="my-artwork-list-header">
      <h3>닉네임의 작품</h3>
      <!-- <p>{{user.nickname}}의 작품</p> -->
    </div>
    <div>
      <masonry-wall
        :items="my_artwork_list"
        :ssr-columns="1"
        :column-width="300"
        :gap="16"
      >
        <template #default="{ item }">
          <div class="thumbnail-wrapper">
            <img :src="item.image" :alt="`${item.id}`" />
            <div class="overlay">
              <div class="info">
                <span style="color: white" class="artwork-title"
                  >작품제목 #{{ item.id }}</span
                >
                <span style="color: white" class="artwork-artist">{{
                  item.artist
                }}</span>
              </div>
              <div class="button-top-right">
                <like-button :liked="!valid" @click="handleLike"></like-button>
              </div>
            </div>
          </div>
        </template>
      </masonry-wall>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import LikeButton from "../common/LikeButton.vue";
import axios from "axios";

export default defineComponent({
  data() {
    return {
      // 회원 닉네임
      // 회원 아이디
      // 좋아요 여부
      // 그림 게시 날짜 (최신순 보여주기 가정) 필터 추가해서 좋아요 많은 순추가할수도
      // 그림 저장 경로
      // 작품 번호
      my_artwork_list: [] as any,
      page: 1,
      valid: true,
    };
  },
  components: {
    LikeButton,
  },
  methods: {
    getUsersArtwork() {
      console.log("got it!");
    },
    handleLike() {
      this.valid = !this.valid;
    },
    getArtwork() {
      // API완성되면 전체작품조회 API 요청이 들어갈 곳
      const options = {
        params: {
          _page: this.page++,
          _limit: 30,
        },
      };
      this.page++;
      axios
        .get("https://picsum.photos/v2/list", options)
        .then((res) => {
          const temp = res.data;
          const artwork = [];
          for (let i = 0; i < 30; i++) {
            artwork.push({
              id: temp[i].id,
              // title: temp[i].title,
              artist: temp[i].author,
              image: temp[i].download_url,
            });
            console.log(artwork);
          }
          // 이미지 로드 시간 때문에 함수 호출이 잦은 것 같은데 해결할 방법 없나?
          this.my_artwork_list = [...this.my_artwork_list, ...artwork];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleScroll() {
      if (
        window.scrollY + window.innerHeight >=
        document.body.scrollHeight - 50
      ) {
        this.getArtwork();
      }
    },
  },
  mounted() {
    this.getArtwork();
    window.addEventListener("scroll", this.handleScroll);
  },
});
</script>

<style lang="scss" scoped>
// container
.container {
  max-width: 1200px;
  min-width: 320px;
  margin: 0 auto;
  padding: 0 1rem;
}

// my artwork list
.my-artwork-list-header {
  & h3 {
    display: inline-block;
  }
}

// masonry
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

.info {
  position: absolute;
  bottom: $font-small;
  left: 10px;
  visibility: hidden;
  z-index: 3;
  transition: 0.4s, ease-in-out;
}

.button-top-right {
  position: absolute;
  top: 2px;
  right: 2px;
  z-index: 3;
  visibility: hidden;
}
</style>
