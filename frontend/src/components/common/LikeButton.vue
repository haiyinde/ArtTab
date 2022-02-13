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
import LikesAPI from "@/apis/likesAPI";
import ResponseData from "@/types/ResponseData";
import { Heart, HeartOutline } from "mdue";

export default defineComponent({
  name: "LikeButton",
  props: {
    liked: {
      type: Boolean,
      required: true,
    },
    artworkId: {
      type: Number,
      required: true,
    },
    userId: {
      type: Number,
      required: true,
    },
  },
  components: {
    Heart,
    HeartOutline,
  },
  methods: {
    // 좋아요 등록 및 취소
    toggleLike() {
      // 좋아요 등록
      if (!this.liked) {
        LikesAPI.addLike({
          artworkId: this.artworkId,
          memberId: this.userId,
        })
          .then((res: ResponseData) => {
            if (res.data === "success") {
              this.$emit("toggle", true);
            } else {
              this.$emit("message", "이미 좋아요 등록된 작품입니다.");
            }
          })
          .catch(() => {
            this.$emit("message", "좋아요 등록 중 오류가 발생했습니다.");
          });
      }
      // 좋아요 취소
      else {
        LikesAPI.deleteLike({
          artworkId: this.artworkId,
          memberId: this.userId,
        })
          .then((res: ResponseData) => {
            if (res.data === "success") {
              this.$emit("toggle", false);
            } else {
              this.$emit("message", "이미 좋아요 취소된 작품입니다.");
            }
          })
          .catch(() => {
            this.$emit("message", "좋아요 취소 중 오류가 발생했습니다.");
          });
      }
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
