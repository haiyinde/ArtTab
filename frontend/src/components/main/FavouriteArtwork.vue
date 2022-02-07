<template>
  <masonry-wall :items="artwork_list" :column-width="300" :gap="16">
    <template #default="{ item }">
      <div>
        <div class="card" :style="{height: `${item.height}px`}">
          <img :src="item.image" :alt="item.title" />
          <div class="info">
            <!-- <h3>작품제목{{item.id}}</h3> -->
            <!-- <p>{{item.artist}}</p> -->
            <!-- <p>{{item.height}}</p> -->
            <!-- 버튼 컴포넌트 :작품 컴포넌트 생기면 id 값 넣어서 개별적으로 달아줘야함 -->
            <like-button
              :disabled="!valid"
              @click="handleLike"
              ></like-button>
            <!-- <a href="#" class="btn"></a> -->
          </div>
        </div>
      </div>
    </template>
  </masonry-wall>
</template>


<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import LikeButton from "./child/LikeButton.vue"

export default defineComponent({
  data() {
    return {
      artwork_list: [] as any,
      page: 1,
      valid: true,
    };
  },
  components: {
    LikeButton,
  },
  methods: {
    handleLike() {
      console.log('like button clicked!');
      this.valid = !this.valid;
    },
    getArtwork() {
      const options = {
        params: {
          _page: this.page++,
          _limit: 30,
        },
      };
      this.page++;
      // API완성되면 전체작품조회 API 요청이 들어갈 곳
      axios
        .get("https://picsum.photos/v2/list", options)
        .then((res) => {
          const temp = res.data;
          const artwork = [];
          console.log(temp)
          for (let i = 0; i < 30; i++) {
            artwork.push({
              id: temp[i].id,
              // title: temp[i].title,
              artist: temp[i].author,
              height: temp[i].height/20,
              image: temp[i].download_url
              // image: temp[i].url
            })

            // console.log(artwork)
          }
          // 이미지 로드 시간 때문에 함수 호출이 잦은 것 같은데 해결할 방법 없나?
          this.artwork_list = [...this.artwork_list, ...artwork];
          // console.log(temp);
          // console.log(this.artwork_list);
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


<style scoped lang="scss">
* {
  box-sizing: border-box;
}

html, body {
  margin: auto;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  // 카드 width 
  width: 300px;
  padding: 2rem 1rem;
  position: relative;
  display: flex;
  align-items: flex-end;
  box-shadow: 0px 7px 10px rgba(0, 0, 0, 0.5);
  transition: 0.5s linear;
  // 아직 개발 X 부분
  &:before {
    content: "";
    position: absolute;
    bottom: 5%;
    left: 5%;
    display: block;
    width: 90%;
    height: 40%;
    background: $white;
    border-radius: 3px;
    z-index: 2;
    transition: 0.5s all;
    // opacity 0 으로 우선 안보이게만 해놓음
    opacity: 0;
  }

  & img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  // 작품명, 작가명, 사진, 좋아요 버튼 box
  & .info {
  position: relative;
  z-index: 3;
  opacity: 0;
  transform: translateY(30px);
  transition: 0.5s all;
    & h3 {
      font-weight: bold;
      margin: 0;
    }
  }

  &:hover {
  transform: translateY(-5px);
    & .info {
      opacity: 1;
      transform: translateY(0px);
    }
  }
}

// .card .info h3 {
//   font-weight: bold;
//   margin: 0;
// }

.card .info p {
  letter-spacing: 1px;
  font-size: 15px;
  margin-top: 8px;
  margin-bottom: 20px;
}

.card .info .btn {
  text-decoration: none;
  padding: 0.5rem;
  background: #fff;
  color: #000;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: 0.4s, ease-in-out;
}

</style>