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
              @click="artworkUpdate"
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
        <like-button
          class="icon"
          :liked="artwork.likeOrNot"
          :artworkId="artworkId"
          :userId="userInfo.id"
          @toggle="toggleLike"
          @message="showToastMessage"
        ></like-button>
        <div class="text">{{ artwork.likeNum }}</div>
        <link-variant class="icon" @click="copyClipboard"></link-variant>
        <calendar-clock class="icon icon-date"></calendar-clock>
        <div class="text">
          {{ computedDate }}
        </div>
      </div>
      <div class="desc">{{ artwork.desciption }}</div>
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
      // 백에서 보내주는 정보 구조에 따라 변경 필요
      // url에 추가해서 얻어오기(url복사를 위해 작품별 별도 url 필요)
      artworkId: Number(this.$route.params.id),
      artwork: {
        artworkSaveFolder:
          // "https://cdn.pixabay.com/photo/2018/07/18/15/43/animal-3546613_960_720.jpg",
          "https://cdn.pixabay.com/photo/2019/05/04/15/24/art-4178302_960_720.jpg",
        desciption:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec odio urna, lobortis at finibus vitae, consectetur eu neque. Pellentesque feugiat id eros nec imperdiet. Proin in magna eget nibh volutpat varius eget eu urna. Quisque tempor tincidunt tellus. Vivamus ac dignissim mauris. Sed non porttitor erat. Fusce in rhoncus lectus. Sed feugiat leo at ante auctor tincidunt.",
        followOrNot: false,
        likeNum: 4652,
        likeOrNot: false,
        regdate: "2022-02-10T09:54:24.762Z",
        title: "여우",
        writerEmail: "ssafy@gmail.com",
        writerId: 1,
        writerNickname: "내이름은여우",
        writerProfileSaveFolder:
          "https://cdn.pixabay.com/photo/2021/03/14/11/14/fish-6093991_960_720.jpg",
      },
    };
  },
  mounted() {
    // 작품 상세 정보 불러온 후
    this.getArtworkDetail();
    // 팔로우 및 좋아요 여부에 따라 버튼 변경하기
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
          console.log(res);
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
      font-size: $font-large;
      font-weight: $weight-bold;
    }
    .profile-writer {
      display: inline-block;
      margin-top: $size-small;
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
