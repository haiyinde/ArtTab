<template>
  <article class="container">
    <div class="img-box">
      <img :src="artwork.artworkSaveFolder" :alt="artwork.title" />
    </div>
    <div class="artworkdesc">
      <div class="profile">
        <div class="profile-imgbox">
          <img :src="artwork.writerProfileSaveFolder" alt="작가 프로필 사진" />
        </div>
        <div>
          <div class="profile-title">{{ artwork.title }}</div>
          <div>
            <div class="profile-writer">by. {{ artwork.writerNickname }}</div>
            <!-- 본인 작품일 경우 follow버튼 대신 수정 버튼-->
            <!-- 수정하기 버튼 클릭시 수정 페이지로 데이터 전달 -->
            <button
              v-if="artwork.writerId === userInfo.id"
              class="btn-white"
              @click="moveArtworkUpdate"
            >
              수정하기
            </button>
            <follow-button
              v-else
              :class="{ 'btn-white': artwork.followOrNot }"
              :followed="artwork.followOrNot"
              :writerId="artwork.writerId"
              :userId="userInfo.id"
              @toggle="toggleFollow"
              @message="showToastMessage"
            >
            </follow-button>
          </div>
        </div>
      </div>
      <div class="additionalinfo">
        <div class="like-box">
          <like-button
            class="icon"
            :liked="artwork.likeOrNot"
            :artworkId="artworkId"
            :userId="userInfo.id"
            @toggle="toggleLike"
            @message="showToastMessage"
          ></like-button>
          <div>{{ artwork.likeNum }}</div>
        </div>
        <link-variant class="icon" @click="copyClipboard"></link-variant>
        <calendar-clock class="icon icon-date"></calendar-clock>
        <div>{{ computedDate }}</div>
      </div>
      <div class="desc">{{ artwork.description }}</div>
    </div>
    <artwork-comments :artworkid="artworkId"></artwork-comments>
    <toast-message ref="toast"></toast-message>
  </article>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ArtworkComments from "@/components/artwork/ArtworkComments.vue";
import LikeButton from "@/components/common/LikeButton.vue";
import FollowButton from "@/components/common/FollowButton.vue";
import ToastMessage from "@/components/common/ToastMessage.vue";
import ArtworkAPI from "@/apis/artworkAPI";
import ResponseData from "@/types/ResponseData";
import ArtworkInfo from "@/types/ArtworkInfo";
import { CalendarClock, LinkVariant } from "mdue";
import { diffTime } from "@/utils/timeDifference";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "ArtworkDetail",
  components: {
    ArtworkComments,
    LikeButton,
    FollowButton,
    ToastMessage,
    CalendarClock,
    LinkVariant,
  },
  data() {
    return {
      artworkId: Number(this.$route.params.id),
      artwork: {} as ArtworkInfo,
    };
  },
  mounted() {
    this.getArtworkDetail();
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
    followText(): string {
      return this.artwork.followOrNot ? "언팔로우" : "팔로우";
    },
    computedDate(): string {
      return diffTime(this.artwork.regdate);
    },
  },
  methods: {
    getArtworkDetail() {
      // 작품 상세 정보 불러오기
      ArtworkAPI.getArtworkById(
        Number(this.artworkId),
        Number(this.userInfo.id)
      )
        .then((res: ResponseData) => {
          console.log(res.data);
          this.artwork = res.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // 좋아요 상태 변경
    toggleLike(res: boolean) {
      this.artwork.likeOrNot = res;
      this.artwork.likeNum = res
        ? this.artwork.likeNum + 1
        : this.artwork.likeNum - 1;
    },
    toggleFollow(res: boolean) {
      this.artwork.followOrNot = res;
    },
    // 클립보드 복사
    copyClipboard() {
      // 현재 URL 가져오기
      const url = window.document.location.href;
      const input = document.createElement("input");

      input.value = url;
      input.setAttribute("readonly", "");
      input.style.position = "absolute";
      input.style.left = "-999px";
      document.body.appendChild(input);
      input.select();
      const result = document.execCommand("copy");
      document.body.removeChild(input);

      if (result) {
        this.showToastMessage("주소가 복사되었습니다.");
      } else {
        this.showToastMessage("주소 복사가 지원되지 않는 브라우저입니다.");
      }
    },
    showToastMessage(msg: string) {
      (this.$refs["toast"] as typeof ToastMessage).showToast(msg);
    },
    moveArtworkUpdate() {
      this.$router.push({
        name: "ArtworkUpdate",
        query: {
          artworkId: this.artworkId,
          title: this.artwork.title,
          description: this.artwork.description,
          artworkSaveFolder: this.artwork.artworkSaveFolder,
        },
      });
    },
  },
});
</script>

<style lang="scss" scoped>
.container {
  width: 70%;
  max-width: 900px;
  margin: 2rem auto;
}

.img-box {
  text-align: center;
  border: 1px solid $grey;
  img {
    width: 100%;
    max-height: 600px;
    object-fit: contain;
  }
}

.artworkdesc {
  margin: $size-medium $size-small;
  .profile {
    position: relative;
    display: flex;
    align-items: center;
    .profile-imgbox {
      width: 4.5rem;
      height: 4.5rem;
      margin-right: $size-medium;
      border-radius: 50%;
      overflow: hidden;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    .profile-title {
      margin-bottom: $size-small;
      font-size: $font-large;
      font-weight: $weight-bold;
    }
    .profile-writer {
      display: inline-block;
      margin-right: $size-small;
    }
  }
  .desc {
    line-height: $size-large;
  }
}

.additionalinfo {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: $size-small;
  color: $dark-grey;
  .icon {
    font-size: 1.5rem;
    cursor: pointer;
    margin-left: $size-small;
    margin-right: $size-micro;
    &.icon-date {
      cursor: auto;
      font-size: $font-medium;
    }
  }
  .like-box {
    display: flex;
    align-items: center;
  }
}

button {
  padding: $size-micro $size-medium;
  font-size: 0.8rem;
  background-color: $black;
  color: $white;
  border: 1px solid $black;
  border-radius: $size-micro;
  cursor: pointer;
  &.btn-white {
    background-color: $white;
    color: $black;
  }
}

/* 모바일 크기에서 적용되는 스타일 */
@media screen and (max-width: 640px) {
  .container {
    width: 100%;
    margin: 0;
  }
  .img-box {
    border: none;
  }
  .artworkdesc {
    .profile {
      img {
        width: 3.8rem;
      }
    }
  }
}
</style>
