<template>
  <!-- Modal -->
  <!-- Profile Edit Modal -->
  <transition name="fade" appear>
    <div class="overlay" v-if="isOpen == true" @click="closeEditModal"></div>
  </transition>
  <transition
    mode="out-in"
    enter-active-class="animate__animated animate__fadeInUp"
    leave-active-class="animate__animated animate__fadeOutDown"
  >
    <div class="modal" v-if="isOpen == true">
      <h2>내 정보 수정</h2>
      <div class="profile-image" style="float: none">
        <img src="https://via.placeholder.com/150/92c952" />
      </div>
      <close-button
        :closed="isClose"
        class="profile-close-btn"
        @click="closeEditModal"
      ></close-button>
      <div>
        <button class="change-profile-pic-btn" @click="changeProfilePic">
          프로필 사진 변경
        </button>
        <label for="nickname" class="label-text"></label>
        <input
          type="nickname"
          id="nickname"
          class="input-text"
          name="nickname"
        />
        <label for="intro" class="label-text">소개</label>
        <input type="intro" id="intro" class="input-text" name="intro" />
        <button class="done-profile-edit-btn" @click="doneEditInfo">
          정보 수정 완료
        </button>
      </div>
    </div>
  </transition>

  <!-- Change Password and Sign Out Modal -->
  <transition name="fade" appear>
    <div
      class="overlay"
      v-if="isCPSOpen == true"
      @click="closeChangePwdModal"
    ></div>
  </transition>
  <transition
    mode="out-in"
    enter-active-class="animate__animated animate__fadeInUp"
    leave-active-class="animate__animated animate__fadeOutDown"
  >
    <div class="modal" v-if="isCPSOpen == true">
      <close-button
        :closed="isClose"
        class="profile-close-btn"
        @click="closeChangePwdModal"
      ></close-button>
      <h2>비밀번호 변경 및 계정 탈퇴</h2>
      <!-- 현재 비밀번호 -->
      <label for="currentPwd" class="label-text">현재 비밀번호</label>
      <input-password
        :password="currentPwd"
        :placetext="'기존 비밀번호를 입력해주세요.'"
        id="currentPwd"
        @inputVal="updateCurrentPwd"
      ></input-password>
      <!-- 변경할 비밀번호 입력 -->
      <label for="password" class="label-text">새 비밀번호</label>
      <input-password
        :password="account.password"
        :placetext="'영문, 특수문자 포함 8자리 이상'"
        id="password"
        @inputVal="updatePassword"
      ></input-password>
      <span class="alert" v-show="valid.password"
        >영문, 특수문자 포함 8자리 이상 입력해주세요.</span
      >
      <!-- 변경할 비밀번호 확인 -->
      <label for="checkPwd" class="label-text">새 비밀번호 다시 입력</label>
      <input-password
        :password="checkPwd"
        :placetext="'비밀번호를 다시 입력해주세요.'"
        id="checkPwd"
        @inputVal="updatecheckPwd"
      ></input-password>
      <span class="alert" v-show="valid.checkPwd"
        >비밀번호가 일치하지 않습니다.</span
      >
      <!-- 비밀번호 변경 완료 여부 모달 추가해야함 -->
      <p class="alert" v-show="canChangePwd">비밀번호 변경에 성공했습니다.</p>
      <p class="alert" v-show="!canChangePwd">비밀번호 변경에 실패했습니다.</p>
      <div>
        <button class="done-change-password-btn" @click="changePassword">
          변경 완료
        </button>
      </div>
      <div class="signout">
        <p class="change-pwd-signout-text" @click="openSignOutModal">
          회원 탈퇴
        </p>
      </div>
    </div>
  </transition>
  <!-- Sign Out Confirmation Modal -->
  <transition name="fade" appear>
    <div
      class="overlay"
      v-if="isSignoutOpen == true"
      @click="closeSignOutModal"
    ></div>
  </transition>
  <transition
    mode="out-in"
    enter-active-class="animate__animated animate__fadeInUp"
    leave-active-class="animate__animated animate__fadeOutDown"
  >
    <div class="modal" v-if="isSignoutOpen == true">
      <h2 class="confirm-signout">정말 탈퇴하시겠습니까?</h2>
      <close-button
        :closed="isClose"
        class="profile-close-btn"
        @click="closeSignOutModal"
      ></close-button>
      <div class="signout-btn">
        <button class="confirm-signout-btn" @click="signOut">탈퇴하기</button>
        <button class="return-signout-btn" @click="closeSignOutModal">
          돌아가기
        </button>
      </div>
    </div>
  </transition>

  <!-- Profile Info -->
  <div>
    <div class="container">
      <div class="profile">
        <div class="profile-image">
          <img
            :src="profileInfo.ImageUrl"
            alt="Profile Image"
          />
        </div>
        <div class="profile-user-settings">
          <h1 class="profile-user-nickname">{{profileInfo.nickname}}</h1>
          <p class="profile-user-email">{{profileInfo.email}}</p>
          <button
            v-if="userInfo.id == this.$route.params.id"
            class="btn profile-edit-btn"
            @click="openEditModal"
          >
            내 정보 수정
          </button>
          <follow-button
            v-if="userInfo.id != this.$route.params.id"
            class="btn profile-edit-btn"
            :followed="follow"
            @click="handleFollow"
          ></follow-button>
        </div>
        <div class="profile-stats">
          <ul>
            <li><span class="profile-stat-count">{{profileInfo.artworkNum}}</span> 게시물</li>
            <li><span class="profile-stat-count">{{profileInfo.followedNum}}</span> 팔로워</li>
            <li><span class="profile-stat-count">{{profileInfo.followingNum}}</span> 팔로잉</li>
          </ul>
        </div>
        <div class="profile-intro">
          <p v-if="profileInfo.intro">
            {{profileInfo.intro}}
          </p>
          <p v-else>
            <b>내 정보 수정버튼</b>을 클릭해 아트탭 회원들에게 자기소개를 해보세요!
          </p>
        </div>
        <div class="change-pwd-signout">
          <p
            v-if="userInfo.id == profileInfo.id"
            class="change-pwd-signout-text"
            @click="openChangePwdModal"
          >
            비밀번호 변경 및 계정 탈퇴
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import FollowButton from "./child/FollowButton.vue";
import InputPassword from "../accounts/child/InputPassword.vue";
import CloseButton from "../common/CloseButton.vue";
import AccountsAPI from "@/apis/accountsAPI";
import PV from "password-validator"; // 비밀번호 유효성 검사 라이브러리
import ResponseData from "@/types/ResponseData";
import { mapState } from "vuex";
import ProfileInfo from "@/types/ProfileInfo"

const accountsStore = "accountsStore";

export default defineComponent({
  data() {
    return {
      // 내 프로필 조회 정보
      account: {
        password: "",
      },
      updateInfo: {
        password: "",
        newPassword: "",
      },
      // 타인 프로필 조회 정보
      profileInfo: {} as ProfileInfo,
      checkPwd: "",
      currentPwd: "",
      follow: false,
      valid: {
        password: false,
        checkPwd: false,
      },
      canChangePwd: false,
      isShowPwd: false,
      // Modal
      isOpen: false,
      isClose: false,
      isCPSOpen: false, // is Change Password Signout Open
      isSignoutOpen: false,
      passwordSchema: new PV(),
    };
  },
  mounted() {
    this.getProfileInfo();
  },
  computed: {
    ...mapState(accountsStore, ["userInfo"]),
  },
  props: {
    id: {
      type: Number,
    },
  },
  components: {
    FollowButton,
    InputPassword,
    CloseButton,
  },
  created() {
    // 영문, 특수문자 포함 8자리 이상 50자리 이하
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(50)
      .has()
      .letters()
      .has()
      .symbols();
  },
  watch: {
    "account.password": function () {
      this.validatePassword();
    },
    checkPwd: function () {
      this.checkPassword();
    },
  },
  methods: {
    handleFollow() {
      this.follow = !this.follow;
    },
    openEditModal() {
      this.isOpen = true;
    },
    closeEditModal() {
      this.isOpen = false;
    },
    doneEditInfo() {
      // 정보 수정 담아서 BE로 보내는 method
      this.closeEditModal();
    },
    // 수정 필요
    async changeProfilePic() {
      // 프로필 사진 변경 정보 담아서 BE로 보내는 method
      // 사진 변경 완료, 실패 modal도 있으면 좋을듯
      await AccountsAPI.updateProfileIntro(
        this.userInfo.email,
        this.userInfo.intro
      ).then((res: ResponseData) => {
        if (res.data === "success") {
          console.log("자기소개 변경에 성공했습니다.");
        } else {
          console.log("자기소개 변경에 실패했습니다.");
        }
      });
      this.closeEditModal();
    },
    openChangePwdModal() {
      this.isCPSOpen = true;
    },
    closeChangePwdModal() {
      this.isCPSOpen = false;
    },
    // 변경할 비밀번호 유효성 검사
    validatePassword() {
      if (!this.passwordSchema.validate(this.account.password)) {
        this.valid.password = true;
        return;
      }
      this.valid.password = false;
    },
    // 비밀번호 변경
    async changePassword() {
      if (!this.valid.password && !this.valid.checkPwd) {
        await AccountsAPI.updatePassword(
          this.userInfo.email,
          this.updateInfo
        ).then((res: ResponseData) => {
          if (res.data === "success") {
            console.log("비밀번호 변경에 성공했습니다.");
            this.canChangePwd = true;
          } else {
            console.log("비밀번호 변경에 실패했습니다.");
            this.canChangePwd = false;
          }
        });
      }
    },
    // 비밀번호와 비밀번호 확인 입력값의 일치 여부 체크
    checkPassword() {
      if (this.account.password !== this.checkPwd) {
        this.valid.checkPwd = true;
        return;
      }
      this.valid.checkPwd = false;
      return;
    },
    openSignOutModal() {
      this.closeChangePwdModal();
      this.isSignoutOpen = true;
    },
    closeSignOutModal() {
      this.isSignoutOpen = false;
    },
    signOut() {
      // 회원탈퇴 요청
      // 회원 탈퇴 처리되었습니다 팝업 open
    },
    // 비밀번호 컴포넌트에 입력된 텍스트 가져오기
    updateCurrentPwd(value: string) {
      this.currentPwd = value;
    },
    updatePassword(value: string) {
      this.account.password = value;
    },
    updatecheckPwd(value: string) {
      this.checkPwd = value;
    },
    // Profile 정보 가져오기
    // 수정 필요
    getProfileInfo() {
      AccountsAPI.getProfileInfo(this.userInfo.id, Number(this.$route.params.id))
        .then((res: ResponseData) => {
          // console.log(res.data);
          this.profileInfo = res.data;
          console.log(this.profileInfo);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
});
</script>

<style scoped lang="scss">
// accounts에서 input css 이용
@import "@/assets/css/accounts.scss";

// signout confirmation modal
.signout-btn {
  display: flex;
  & .confirm-signout-btn {
    font-size: $font-regular;
    line-height: 1.8;
    border: 1.5px solid $black;
    border-radius: 0.3rem;
    background: $white;
    color: $black;
    width: 30%;
    margin: 0 auto;
  }

  & .return-signout-btn {
    font-size: $font-regular;
    line-height: 1.8;
    border: 0.1rem solid $black;
    border-radius: 0.3rem;
    background: $black;
    color: $white;
    width: 30%;
    margin: 0 auto;
  }
}

// my profile info edit modal
.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 98;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  padding: 20px;
}
.modal {
  position: fixed;
  right: 0;
  left: 0;
  margin: 0 auto;
  z-index: 99;
  max-width: 40rem;
  // mobile 크기
  min-width: 320px;
  background-color: $white;
  border-radius: 16px;
  padding: 20px;
  & .confirm-signout {
    display: flex;
    justify-content: center;
    margin-bottom: 3rem;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.change-profile-pic-btn {
  font-size: $font-regular;
  line-height: 1.8;
  border: 1.5px solid $black;
  border-radius: 0.3rem;
  background: $white;
  color: $black;
}

.done-profile-edit-btn {
  font-size: $font-regular;
  line-height: 1.8;
  border: 0.1rem solid $black;
  border-radius: 0.3rem;
  background: $black;
  color: $white;
}

.done-change-password-btn {
  font-size: $font-regular;
  line-height: 1.8;
  border: 0.1rem solid $black;
  border-radius: 0.3rem;
  background: $black;
  color: $white;
  margin: 1rem 0 1rem 0;
}

.change-pwd-signout-text {
  color: $grey;
  font-size: $font-small;
  margin: 0;
  &:hover {
    transition: opacity 2s;
    color: $dark-grey;
    text-decoration-line: underline;
  }
}

.signout {
  float: right;
}

.profile-close-btn {
  font-size: $font-large;
  cursor: pointer;
  position: absolute;
  right: 1rem;
  top: 1rem;
}

// profile
:root {
  font-size: 10px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: "Open Sans", Arial, sans-serif;
  min-height: 100vh;
  background-color: #fafafa;
  color: #262626;
  padding-bottom: 3rem;
}

img {
  display: block;
}

.container {
  max-width: 1200px;
  min-width: 320px;
  margin: 0 auto;
  padding: 0 1rem;
  border: 1px solid $grey;
}

.btn {
  font: inherit;
  background: none;
  border: none;
  color: inherit;
  padding: 0;
  cursor: pointer;
}

.visually-hidden {
  position: absolute !important;
  height: 1px;
  width: 1px;
  overflow: hidden;
  clip: rect(1px, 1px, 1px, 1px);
}

/* Profile Section */

.profile {
  padding: 5rem 0;
}

.profile::after {
  content: "";
  display: block;
  clear: both;
}

.profile-image {
  float: left;
  width: calc(33.333% - 1rem);
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-image img {
  border-radius: 50%;
}

.profile-user-settings,
.profile-stats,
.profile-intro {
  float: left;
  width: calc(66.666% - 2rem);
}

.profile-user-settings {
  margin-top: 1.1rem;
  width: auto;
}

.profile-user-nickname {
  display: block;
  float: left;
  font-size: $font-large;
  font-weight: $weight-semi-bold;
  // user-name 글자가 안무너지게하기위해 임시값(15rem) 지정 username 길이가 달라지면 수치도 변할텐데 이건 모르겠음 ㅠㅠ
  min-width: 15rem;
}

.profile-user-email {
  display: block;
  color: $dark-grey;
  margin: auto;
  font-weight: $weight-light;
}

.profile-edit-btn {
  font-size: $font-regular;
  line-height: 1.8;
  border: 1.5px solid $black;
  border-radius: 0.3rem;
  margin: 1rem 0 0.2rem 0;
  // hover시 색상 전환 천천히
  transition: border-color 0.5s, background-color 0.5s, color 0.5s;
  // hover시 색상 전환
  &:hover {
    background-color: $black;
    color: $white;
    border-color: transparent;
  }
}

.profile-stats {
  margin: 1rem auto 0 auto;
}

.profile-stats li {
  display: inline-block;
  font-size: 1.6rem;
  line-height: 1.5;
  margin-right: 2rem;
  cursor: pointer;
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.profile-intro {
  font-size: 1.6rem;
  font-weight: 400;
  line-height: 1.5;
  margin-top: 1rem;
}

.profile-stat-count,
.profile-edit-btn {
  font-weight: $weight-semi-bold;
}

/* Media Query */
// 40rem이하

@media screen and (max-width: 40rem) {
  .profile {
    display: flex;
    flex-wrap: wrap;
    padding: 4rem 0;
  }

  .profile::after {
    display: none;
  }

  .profile-image,
  .profile-user-settings,
  .profile-intro,
  .profile-stats {
    float: none;
    width: auto;
  }

  .profile-image img {
    width: 7.7rem;
  }

  .profile-user-settings {
    flex-basis: calc(100% - 10.7rem);
    display: flex;
    flex-wrap: wrap;
    margin-top: 1rem;
  }

  .profile-user-nickname {
    display: block;
    font-size: $font-large;
  }

  .profile-user-email {
    display: block;
    font-size: $size-medium;
  }

  .profile-edit-btn {
    order: 1;
    padding: 0;
    text-align: center;
    margin-top: 1rem;
  }

  .profile-edit-btn {
    margin-left: 0;
  }

  .profile-intro {
    font-size: 1.4rem;
    margin-top: 1.5rem;
  }

  .profile-edit-btn,
  .profile-intro,
  .profile-stats {
    flex-basis: 100%;
  }

  .profile-stats {
    order: 1;
    margin-top: 1.5rem;
  }

  .profile-stats ul {
    display: flex;
    text-align: center;
    padding: 1.2rem 0;
    border-top: 0.1rem solid #dadada;
    border-bottom: 0.1rem solid #dadada;
  }

  .profile-stats li {
    font-size: 1.4rem;
    flex: 1;
    margin: 0;
  }

  .profile-stat-count {
    display: block;
  }
}

/* CSS grid */

@supports (display: grid) {
  .profile {
    display: grid;
    grid-template-columns: 1fr 2fr;
    grid-template-rows: repeat(3, auto);
    grid-column-gap: 1.5rem;
    align-items: center;
  }

  .profile-image {
    grid-row: 1 / -1;
  }

  .profile-image,
  .profile-user-settings,
  .profile-stats,
  .profile-intro {
    width: auto;
  }
  .change-pwd-signout {
    grid-column: -2 / -1;
    display: flex;
    justify-content: flex-end;
  }
  // CSS grid 40rem 이하 적용
  @media (max-width: 40rem) {
    .profile {
      grid-template-columns: auto 1fr;
      grid-row-gap: 1.5rem;
    }

    .profile-image {
      grid-row: 1 / 2;
    }

    .profile-user-settings {
      display: grid;
      grid-template-columns: auto 1fr;
      grid-gap: 1rem;
    }

    .profile-edit-btn,
    .profile-stats,
    .profile-intro {
      grid-column: 1 / -1;
    }

    .profile-user-settings,
    .profile-edit-btn,
    .profile-intro,
    .profile-stats {
      margin: 0;
    }

    .change-pwd-signout {
      grid-row: 4 / 5;
    }
  }
}
</style>
