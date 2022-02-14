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
      <div v-show="!isShowUpdate" class="content">{{ comment.content }}</div>
      <div v-show="isShowUpdate">
        <textarea
          class="inputcomment"
          v-model="updatedContent"
          rows="1"
          maxlength="100"
          @keydown="resizeTextarea"
          @keyup.enter="updateComment"
        />
        <button @click="updateComment">수정</button>
        <button @click="closeUpdate">취소</button>
      </div>
    </div>
    <!-- 본인이 쓴 댓글일 때만 보임 -->
    <div v-if="isMyComment" class="option-box">
      <dots-horizontal class="icon" @click="showOption"></dots-horizontal>
      <ul v-show="isShowOption" class="option">
        <li @click="showUpdate">수정</li>
        <li @click="deleteComment">삭제</li>
      </ul>
    </div>
    <div class="overlay" v-show="isShowOption" @click="showOption"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommentsAPI from "@/apis/commentsAPI";
import { diffTime } from "@/utils/timeDifference";
import { DotsHorizontal } from "mdue";
import ResponseData from "@/types/ResponseData";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "CommentItem",
  props: {
    comment: {
      type: Object,
      required: true,
    },
    isMyComment: {
      type: Boolean,
      required: true,
    },
  },
  components: {
    DotsHorizontal,
  },
  data() {
    return {
      isShowOption: false,
      isShowUpdate: false,
      updatedContent: this.comment.content,
    };
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
    computedDate(): string {
      return diffTime(this.comment.regdate);
    },
  },
  methods: {
    showOption() {
      this.isShowOption = !this.isShowOption;
    },
    showUpdate() {
      this.isShowUpdate = true;
      this.isShowOption = false;
    },
    closeUpdate() {
      this.isShowUpdate = false;
      this.updatedContent = this.comment.content;
    },
    updateComment() {
      CommentsAPI.updateComment(this.comment.id, {
        content: this.updatedContent,
        email: this.userInfo.email,
      }).then((res: ResponseData) => {
        this.$emit("getList");
        console.log(res);
      });
    },
    deleteComment() {
      if (!confirm("정말 삭제하시겠습니까?")) return;
      CommentsAPI.deleteComment(this.comment.id).then((res: ResponseData) => {
        this.$emit("getList");
        console.log(res);
      });
    },
    resizeTextarea() {
      const area = document.querySelector(
        ".inputcomment"
      ) as HTMLTextAreaElement;
      console.log(area.scrollHeight);
      area.style.height = "auto";
      area.style.height = `${area.scrollHeight}px`;
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
  width: 100%;
  font-size: $font-small;
  .name {
    display: inline-block;
    font-weight: $weight-bold;
    margin: $size-micro $size-medium $size-micro 0;
  }
  .content {
    line-height: 1.1rem;
  }
  textarea {
    width: 100%;
    min-height: 1rem;
    padding: $size-small;
    resize: none;
    overflow-y: hidden;
    border: 1px solid $grey;
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
    z-index: 10;
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
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 5;
}
</style>
