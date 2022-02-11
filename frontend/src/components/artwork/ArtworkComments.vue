<template>
  <section class="comments-box">
    <div>댓글({{ commentCount }})</div>
    <comment-item
      v-for="(comment, idx) in commentList"
      :key="idx"
      :comment="comment"
    ></comment-item>
    <div class="input-box">
      <input type="text" placeholder="댓글 달기" />
      <send class="icon-send"></send>
    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommentItem from "@/components/artwork/child/CommentItem.vue";
import CommentInfo from "@/types/CommentInfo";
import { Send } from "mdue";

export default defineComponent({
  name: "ArtworkComments",
  components: {
    CommentItem,
    Send,
  },
  data() {
    return {
      commentList: [
        {
          id: 1,
          artwork_id: 1,
          member_email: "string@mail.com",
          modifiedDate: "2022-02-10T10:59:48.721729",
          content: "귀여워요",
          nickName: "스트링",
          imgUrl:
            "https://cdn.pixabay.com/photo/2022/01/27/19/14/flowers-6972916_960_720.jpg",
        },
        {
          id: 2,
          artwork_id: 1,
          member_email: "ssafy@mail.com",
          modifiedDate: "2022-02-10T10:59:48.721729",
          content:
            "잘 그리셨네요! 색감도 정말 좋습니다. 배우고 싶어요~~!! 길게 쓴 댓글입니다. 더 길게 써야하는데 아무 생각이 없습니다.!!",
          nickName: "싸피인",
          imgUrl:
            "https://cdn.pixabay.com/photo/2022/02/05/08/13/tulip-6994245__340.jpg",
        },
      ] as CommentInfo[],
      // 백 연결 시 데이터 불러오면서 길이 넣기
      commentCount: 2,
    };
  },
});
</script>

<style lang="scss" scoped>
.comments-box {
  padding: $size-small;
}

.input-box {
  position: relative;
  input {
    width: 100%;
    padding: $size-small;
    border-bottom: 1px solid $grey;
  }
  .icon-send {
    position: absolute;
    top: 50%;
    right: 0;
    transform: translateY(-50%);
    cursor: pointer;
    &:hover {
      color: $blue;
    }
  }
}
</style>
