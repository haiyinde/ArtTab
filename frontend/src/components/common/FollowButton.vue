<template>
  <button v-text="buttonText" @click="toggleFollow"></button>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import FollowAPI from "@/apis/followAPI";
import ResponseData from "@/types/ResponseData";

export default defineComponent({
  name: "FollowButton",
  props: {
    // user id랑 follows prop 받기
    followed: {
      type: Boolean,
      required: true,
    },
    // followeeId(팔로우 할 상대 사용자)
    writerId: {
      type: Number,
      required: true,
    },
    // followerId(로그인 한 사용자)
    userId: {
      type: Number,
      required: true,
    },
  },
  methods: {
    toggleFollow() {
      // 팔로우 등록
      if (!this.followed) {
        FollowAPI.addFollow({
          followeeId: this.writerId,
          followerId: this.userId,
        })
          .then((res: ResponseData) => {
            if (res.data === "success") {
              this.$emit("toggle", true);
            } else {
              this.$emit("message", "이미 팔로우 등록된 작가입니다.");
            }
          })
          .catch(() => {
            this.$emit("message", "팔로우 등록 중 오류가 발생했습니다.");
          });
      }
      // 팔로우 취소
      else {
        FollowAPI.deleteFollow({
          followeeId: this.writerId,
          followerId: this.userId,
        })
          .then((res: ResponseData) => {
            if (res.data === "success") {
              this.$emit("toggle", false);
            } else {
              this.$emit("message", "이미 언팔로우된 작가입니다.");
            }
          })
          .catch(() => {
            this.$emit("message", "팔로우 취소 중 오류가 발생했습니다.");
          });
      }
    },
  },
  computed: {
    buttonText() {
      return this.followed ? "언팔로우" : "팔로우";
    },
  },
});
</script>

<style lang="scss" scoped>
button {
  font-weight: $weight-semi-bold;
  &:hover {
    color: $white;
  }
}
</style>
