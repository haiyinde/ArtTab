<template>
  <div>
    <heart-outline
      v-show="!liked"
      class="icon"
      @click="toggleLike"
    ></heart-outline>
    <heart v-show="liked" class="icon liked-color" @click="toggleLike"></heart>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Heart, HeartOutline } from "mdue";

export default defineComponent({
  props: {
    liked: {
      type: Boolean,
    },
  },
  components: {
    Heart,
    HeartOutline,
  },
  methods: {
    toggleLike() {
      // api 좋아요 추가 호출하기 -> 결과 성공하면 토글, 실패하면 X
      this.$emit("toggle", !this.liked);
    },
  },
});
</script>

<style lang="scss" scoped>
.icon {
  cursor: pointer;
}
.liked-color {
  color: $red;
}
</style>
