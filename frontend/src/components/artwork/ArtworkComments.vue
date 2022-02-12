<template>
  <section class="comments-box">
    <div>댓글({{ commentCount }})</div>
    <comment-item
      v-for="(comment, idx) in commentList"
      :key="idx"
      :comment="comment"
    ></comment-item>
    <div class="input-box">
      <input v-model="inputComment" type="text" placeholder="댓글 달기" />
      <send class="icon-send" @click="addComment"></send>
    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommentItem from "@/components/artwork/child/CommentItem.vue";
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
      console.log(this.inputComment);
      console.log(this.userInfo.id);
      await CommentsAPI.addComment(this.artworkid, {
        content: this.inputComment,
        memberId: this.userInfo.id,
      }).then((res: ResponseData) => {
        console.log(res.data);
      });

      this.getCommentList();
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
