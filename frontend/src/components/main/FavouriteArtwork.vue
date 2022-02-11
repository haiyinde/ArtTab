<template>
  <div class="container">
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

  <div class="container">
    <!-- loader -->
    <div class="scene">
      <div class="objects">
        <div class="square"></div>
        <div class="circle"></div>
        <div class="triangle"></div>
      </div>
      <div class="wizard">
        <div class="body"></div>
        <div class="right-arm">
          <div class="right-hand"></div>
        </div>
        <div class="left-arm">
          <div class="left-hand"></div>
        </div>
        <div class="head">
          <div class="beard"></div>
          <div class="face">
            <div class="adds"></div>
          </div>
          <div class="hat">
            <div class="hat-of-the-hat"></div>
            <div class="four-point-star --first"></div>
            <div class="four-point-star --second"></div>
            <div class="four-point-star --third"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="progress"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import LikeButton from "../common/LikeButton.vue";

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
          this.artwork_list = [...this.artwork_list, ...artwork];
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
/* Spinner Animation */

$primary: #3f64ce;
$skin: #f1c5b4;
$background: #ffffff;

body {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  flex-wrap: wrap;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.scene {
  display: flex;
}

/** Wizard CSS & Animations */

.wizard {
  position: relative;
  width: 190px;
  height: 240px;
}

.body {
  position: absolute;
  bottom: 0;
  left: 68px;
  height: 100px;
  width: 60px;
  background: $primary;
  &::after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 20px;
    height: 100px;
    width: 60px;
    background: $primary;
    transform: skewX(14deg);
  }
}

.right-arm {
  position: absolute;
  bottom: 74px;
  left: 110px;
  height: 44px;
  width: 90px;
  background: $primary;
  border-radius: 22px;
  /** animation */
  transform-origin: 16px 22px;
  transform: rotate(70deg);
  animation: right_arm 10s ease-in-out infinite;
  .right-hand {
    position: absolute;
    right: 8px;
    bottom: 8px;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background: $skin;
    /** animation */
    transform-origin: center center;
    transform: rotate(-40deg);
    animation: right_hand 10s ease-in-out infinite;
    &::after {
      content: "";
      position: absolute;
      right: 0px;
      top: -8px;
      width: 15px;
      height: 30px;
      border-radius: 10px;
      background: $skin;
      /** Animation */
      transform: translateY(16px);
      animation: right_finger 10s ease-in-out infinite;
    }
  }
}

.left-arm {
  position: absolute;
  bottom: 74px;
  left: 26px;
  height: 44px;
  width: 70px;
  background: $primary;
  border-bottom-left-radius: 8px;
  /** animation */
  transform-origin: 60px 26px;
  transform: rotate(-70deg);
  animation: left_arm 10s ease-in-out infinite;
  .left-hand {
    position: absolute;
    left: -18px;
    top: 0;
    width: 18px;
    height: 30px;
    border-top-left-radius: 35px;
    border-bottom-left-radius: 35px;
    background: $skin;
    &::after {
      content: "";
      position: absolute;
      right: 0;
      top: 0;
      width: 30px;
      height: 15px;
      border-radius: 20px;
      background: $skin;
      /** Animation */
      transform-origin: right bottom;
      transform: scaleX(0);
      animation: left_finger 10s ease-in-out infinite;
    }
  }
}

.head {
  position: absolute;
  top: 0;
  left: 14px;
  width: 160px;
  height: 210px;
  /** Animation */
  transform-origin: center center;
  transform: rotate(-3deg);
  animation: head 10s ease-in-out infinite;
  .beard {
    position: absolute;
    bottom: 0;
    left: 38px;
    height: 106px;
    width: 80px;
    border-bottom-right-radius: 55%;
    background: $background;
    &::after {
      content: "";
      position: absolute;
      top: 16px;
      left: -10px;
      width: 40px;
      height: 20px;
      border-radius: 20px;
      background: $background;
    }
  }
  .face {
    position: absolute;
    bottom: 76px;
    left: 38px;
    height: 30px;
    width: 60px;
    background: $skin;
    &::before {
      content: "";
      position: absolute;
      top: 0px;
      left: 40px;
      width: 20px;
      height: 40px;
      border-bottom-right-radius: 20px;
      border-bottom-left-radius: 20px;
      background: $skin;
    }
    &::after {
      content: "";
      position: absolute;
      top: 16px;
      left: -10px;
      width: 50px;
      height: 20px;
      border-radius: 20px;
      border-bottom-right-radius: 0px;
      background: $background;
    }
    .adds {
      /** Nose */
      position: absolute;
      top: 0px;
      left: -10px;
      width: 40px;
      height: 20px;
      border-radius: 20px;
      background: $skin;
      &::after {
        /** Ear */
        content: "";
        position: absolute;
        top: 5px;
        left: 80px;
        width: 15px;
        height: 20px;
        border-bottom-right-radius: 20px;
        border-top-right-radius: 20px;
        background: $skin;
      }
    }
  }
  .hat {
    position: absolute;
    bottom: 106px;
    left: 0;
    width: 160px;
    height: 20px;
    border-radius: 20px;
    background: $primary;
    &::before {
      content: "";
      position: absolute;
      top: -70px;
      left: 50%;
      transform: translatex(-50%);
      width: 0;
      height: 0;
      border-style: solid;
      border-width: 0 34px 70px 50px;
      border-color: transparent transparent $primary transparent;
    }
    &::after {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 160px;
      height: 20px;
      background: $primary;
      border-radius: 20px;
    }
    .hat-of-the-hat {
      position: absolute;
      bottom: 78px;
      left: 79px;
      width: 0;
      height: 0;
      border-style: solid;
      border-width: 0 25px 25px 19px;
      border-color: transparent transparent $primary transparent;
      &::after {
        content: "";
        position: absolute;
        top: 6px;
        left: -4px;
        width: 35px;
        height: 10px;
        border-radius: 10px;
        border-bottom-left-radius: 0px;
        background: $primary;
        transform: rotate(40deg);
      }
    }
    .four-point-star {
      position: absolute;
      width: 12px;
      height: 12px;
      &::after,
      &::before {
        content: "";
        position: absolute;
        background: $background;
        display: block;
        left: 0;
        width: 141.4213%; /* 100% * √2 */
        top: 0;
        bottom: 0;
        border-radius: 10%;
        transform: rotate(66.66deg) skewX(45deg);
      }
      &::after {
        transform: rotate(156.66deg) skew(45deg);
      }
      &.--first {
        bottom: 28px;
        left: 46px;
      }
      &.--second {
        bottom: 40px;
        left: 80px;
      }
      &.--third {
        bottom: 15px;
        left: 108px;
      }
    }
  }
}

/** 10s animation - 10% = 1s */

@keyframes right_arm {
  0% {
    transform: rotate(70deg);
  }
  10% {
    transform: rotate(8deg);
  }
  15% {
    transform: rotate(20deg);
  }
  20% {
    transform: rotate(10deg);
  }
  25% {
    transform: rotate(26deg);
  }
  30% {
    transform: rotate(10deg);
  }
  35% {
    transform: rotate(28deg);
  }
  40% {
    transform: rotate(9deg);
  }
  45% {
    transform: rotate(28deg);
  }
  50% {
    transform: rotate(8deg);
  }
  58% {
    transform: rotate(74deg);
  }
  62% {
    transform: rotate(70deg);
  }
}

@keyframes left_arm {
  0% {
    transform: rotate(-70deg);
  }
  10% {
    transform: rotate(6deg);
  }
  15% {
    transform: rotate(-18deg);
  }
  20% {
    transform: rotate(5deg);
  }
  25% {
    transform: rotate(-18deg);
  }
  30% {
    transform: rotate(5deg);
  }
  35% {
    transform: rotate(-17deg);
  }
  40% {
    transform: rotate(5deg);
  }
  45% {
    transform: rotate(-18deg);
  }
  50% {
    transform: rotate(6deg);
  }
  58% {
    transform: rotate(-74deg);
  }
  62% {
    transform: rotate(-70deg);
  }
}

@keyframes right_hand {
  0% {
    transform: rotate(-40deg);
  }
  10% {
    transform: rotate(-20deg);
  }
  15% {
    transform: rotate(-5deg);
  }
  20% {
    transform: rotate(-60deg);
  }
  25% {
    transform: rotate(-0deg);
  }
  30% {
    transform: rotate(-60deg);
  }
  35% {
    transform: rotate(-0deg);
  }
  40% {
    transform: rotate(-40deg);
  }
  45% {
    transform: rotate(-60deg);
  }
  50% {
    transform: rotate(10deg);
  }
  60% {
    transform: rotate(-40deg);
  }
}

@keyframes right_finger {
  0% {
    transform: translateY(16px);
  }
  10% {
    transform: none;
  }
  50% {
    transform: none;
  }
  60% {
    transform: translateY(16px);
  }
}

@keyframes left_finger {
  0% {
    transform: scaleX(0);
  }
  10% {
    transform: scaleX(1) rotate(6deg);
  }
  15% {
    transform: scaleX(1) rotate(0deg);
  }
  20% {
    transform: scaleX(1) rotate(8deg);
  }
  25% {
    transform: scaleX(1) rotate(0deg);
  }
  30% {
    transform: scaleX(1) rotate(7deg);
  }
  35% {
    transform: scaleX(1) rotate(0deg);
  }
  40% {
    transform: scaleX(1) rotate(5deg);
  }
  45% {
    transform: scaleX(1) rotate(0deg);
  }
  50% {
    transform: scaleX(1) rotate(6deg);
  }
  58% {
    transform: scaleX(0);
  }
}

@keyframes head {
  0% {
    transform: rotate(-3deg);
  }
  10% {
    transform: translatex(10px) rotate(7deg);
  }
  50% {
    transform: translatex(0px) rotate(0deg);
  }
  56% {
    transform: rotate(-3deg);
  }
}

/** Objects CSS & Animations */

.objects {
  position: relative;
  width: 200px;
  height: 240px;
}

.square {
  position: absolute;
  bottom: -60px;
  left: -5px;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  /** Animation */
  transform: rotate(-360deg);
  animation: path_square 10s ease-in-out infinite;
  &::after {
    content: "";
    position: absolute;
    top: 10px;
    left: 0;
    width: 50px;
    height: 50px;
    background: #9ab3f5;
  }
}

.circle {
  position: absolute;
  bottom: 10px;
  left: 0;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  /** Animation */
  transform: rotate(-360deg);
  animation: path_circle 10s ease-in-out infinite;
  &::after {
    content: "";
    position: absolute;
    bottom: -10px;
    left: 25px;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    background: #c56183;
  }
}

.triangle {
  position: absolute;
  bottom: -62px;
  left: -10px;
  width: 110px;
  height: 110px;
  border-radius: 50%;
  /** Animation */
  transform: rotate(-360deg);
  animation: path_triangle 10s ease-in-out infinite;
  &::after {
    content: "";
    position: absolute;
    top: 0;
    right: -10px;
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 0 28px 48px 28px;
    border-color: transparent transparent #89beb3 transparent;
  }
}

/** 10s animation - 10% = 1s */

@keyframes path_circle {
  0% {
    transform: translateY(0);
  }
  10% {
    transform: translateY(-100px) rotate(-5deg);
  }
  55% {
    transform: translateY(-100px) rotate(-360deg);
  }
  58% {
    transform: translateY(-100px) rotate(-360deg);
  }
  63% {
    transform: rotate(-360deg);
  }
}

@keyframes path_square {
  0% {
    transform: translateY(0);
  }
  10% {
    transform: translateY(-155px) translatex(-15px) rotate(10deg);
  }
  55% {
    transform: translateY(-155px) translatex(-15px) rotate(-350deg);
  }
  57% {
    transform: translateY(-155px) translatex(-15px) rotate(-350deg);
  }
  63% {
    transform: rotate(-360deg);
  }
}

@keyframes path_triangle {
  0% {
    transform: translateY(0);
  }
  10% {
    transform: translateY(-172px) translatex(10px) rotate(-10deg);
  }
  55% {
    transform: translateY(-172px) translatex(10px) rotate(-365deg);
  }
  58% {
    transform: translateY(-172px) translatex(10px) rotate(-365deg);
  }
  63% {
    transform: rotate(-360deg);
  }
}

.progress {
  position: relative;
  margin-top: 60px;
  // margin: auto;
  width: 400px;
  height: 20px;
  background: #eee;
  &::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 0;
    height: 100%;
    background: #637373;
    animation: progress 20s linear infinite;
  }
}

@keyframes progress {
  from {
    width: 0;
  }
  to {
    width: 100%;
  }
}
</style>
