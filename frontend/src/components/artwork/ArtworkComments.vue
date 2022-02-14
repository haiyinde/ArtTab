<template>
  <section class="comments-box">
    <div>댓글({{ commentCount }})</div>
    <div class="input-box">
      <textarea
        v-model="inputComment"
        rows="1"
        maxlength="100"
        placeholder="댓글 달기"
        @keydown="resizeTextarea"
        @keyup.enter="addComment"
      />
      <send class="icon-send" @click="addComment"></send>
    </div>
    <comment-item
      v-for="(comment, idx) in commentList"
      :key="idx"
      :comment="comment"
      :isMyComment="comment.member_id === userInfo.id"
      @getList="getCommentList"
    ></comment-item>
    <toast-message ref="toast"></toast-message>
  </section>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommentItem from "@/components/artwork/child/CommentItem.vue";
import ToastMessage from "@/components/common/ToastMessage.vue";
import CommentInfo from "@/types/CommentInfo";
import { Send } from "mdue";
import CommentsAPI from "@/apis/commentsAPI";
import ResponseData from "@/types/ResponseData";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "ArtworkComments",
  components: {
    CommentItem,
    ToastMessage,
    Send,
  },
  props: {
    artworkid: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      commentList: [] as CommentInfo[],
      commentCount: 0,
      inputComment: "",
    };
  },
  mounted() {
    this.getCommentList();
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  methods: {
    getCommentList() {
      CommentsAPI.getComments(this.artworkid).then((res: ResponseData) => {
        this.commentList = res.data;
        this.commentCount = this.commentList.length;
        console.log(res.data);
      });
    },
    async addComment() {
      if (!this.inputComment) {
        alert("내용을 입력해주세요.");
        this.inputComment = "";
        return;
      }
      await CommentsAPI.addComment(this.artworkid, {
        content: this.inputComment,
        memberId: this.userInfo.id,
      }).then((res: ResponseData) => {
        if (res.data === "success") {
          this.inputComment = "";
        } else {
          (this.$refs["toast"] as typeof ToastMessage).showToast(
            "댓글 등록에 실패했습니다."
          );
        }
      });

      this.getCommentList();
    },
    resizeTextarea() {
      const area = document.querySelector("textarea") as HTMLTextAreaElement;
      area.style.height = "auto";
      area.style.height = `${area.scrollHeight}px`;
    },
  },
});
</script>

<style lang="scss" scoped>
.comments-box {
  padding: $size-small;
}

.input-box {
  position: relative;
  margin-top: $size-large;
  textarea {
    width: 100%;
    min-height: 1rem;
    overflow-y: hidden;
    padding: $size-small;
    padding-right: $size-big;
    resize: none;
    border: 1px solid $grey;
  }
  .icon-send {
    position: absolute;
    top: 50%;
    right: $size-small;
    transform: translateY(-50%);
    cursor: pointer;
    &:hover {
      color: $blue;
    }
  }
}
</style>
