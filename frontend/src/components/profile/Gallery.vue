<template>
  <div class="container">
    <div class="gallery-header">
      <h3>마이 갤러리</h3>
      <router-link class="decorate-gallery-btn" :to="{ name: 'Profile' }"
        >꾸미기</router-link
      >
    </div>
    <div class="gallery-layout">
      <img
        src="https://media.gettyimages.com/vectors/vector-black-picture-frame-set-on-wall-background-vector-id1059930772?k=20&m=1059930772&s=612x612&w=0&h=H8lVTwPZCb4b79WnVTKz96XbIO4KbAVkb4_r5gXEq2s="
        alt=""
      />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  // data(): {
  //   return {
  //     galleryImage: [] as any,
  //   }
  // },
  // methods: {
  //   getGalleryImage() {
  //     // Image API
  //     return this.galleryImage
  //   }
  // },
  // mounted: {
  //   this.getGalleryImage();
  // },
});
</script>

<style lang="scss" scoped>
.container {
  max-width: 1200px;
  min-width: 320px;
  margin: 0 auto;
  padding: 0 1rem;
}

.gallery-layout {
  border: 1.5px solid $black;
  border-radius: 0.3rem;
  // 아마 이건 데이터 받아올 때 height width 고정되어서 CSS에서 만질 일 없을 듯
  margin: 0 auto;
  & img {
    width: 40rem;
  }
}

.gallery-header {
  & h3 {
    display: inline-block;
  }
  & .decorate-gallery-btn {
    font-size: $font-regular;
    font-weight: $weight-semi-bold;
    line-height: 1.8;
    border: 1.5px solid $black;
    border-radius: 0.3rem;
    // 위치
    display: flex;
    float: right;
    // margin-right: 1rem;
    // width: 4rem;
    width: 120px;
    text-align: center;
    align-items: center;
    justify-content: center;
    // hover시 색상 전환 천천히
    transition: border-color 0.5s, background-color 0.5s, color 0.5s;
    // hover시 색상 전환
    &:hover {
      background-color: $black;
      color: $white;
      border-color: transparent;
    }
  }
}
</style>
