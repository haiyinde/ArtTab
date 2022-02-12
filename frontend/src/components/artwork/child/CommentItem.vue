<template>
  <div class="comment-box">
    <div class="profile-imgbox">
      <img :src="comment.imgUrl" alt="작가 프로필 사진" />
    </div>
    <div class="comment-info">
      <div>
        <div class="name">{{ comment.nickName }}</div>
        <span>{{ computedDate }}</span>
      </div>
      <div class="content">{{ comment.content }}</div>
    </div>
    <!-- 본인이 쓴 댓글일 때만 보이도록 해야 함 -->
    <div class="option-box">
      <dots-horizontal class="icon" @click="showOption"></dots-horizontal>
      <ul v-show="isShowOption" class="option">
        <li>수정</li>
        <li>삭제</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { diffTime } from "@/utils/timeDifference";
import { DotsHorizontal } from "mdue";

export default defineComponent({
  name: "CommentItem",
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  components: {
    DotsHorizontal,
  },
  data() {
    return {
      isShowOption: false,
    };
  },
  computed: {
    computedDate(): string {
      return diffTime(this.comment.regdate);
    },
  },
  methods: {
    showOption() {
      this.isShowOption = !this.isShowOption;
    },
  },
});
</script>

<style lang="scss" scoped>
.comment-box {
  position: relative;
  margin-top: $size-small;
  padding: $size-small;
  display: flex;
}

.profile-imgbox {
  width: 3rem;
  height: 3rem;
  flex-shrink: 0;
  margin-right: $size-medium;
  border-radius: 50%;
  overflow: hidden;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.comment-info {
  font-size: $font-small;
  .name {
    display: inline-block;
    font-weight: $weight-bold;
    margin: $size-micro $size-medium $size-micro 0;
  }
  .content {
    line-height: 1.1rem;
  }
}

.option-box {
  position: absolute;
  right: 0;
  display: flex;
  flex-direction: row-reverse;
  cursor: pointer;
  .icon {
    font-size: $size-large;
  }
  .option {
    padding: $size-micro 0;
    margin-right: $size-micro;
    background-color: $white;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    li {
      cursor: pointer;
      font-size: $font-small;
      padding: $size-micro $size-large;
      &:hover {
        background-color: $light-grey;
      }
    }
  }
}
</style>
