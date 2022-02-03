<template>
  <div>
    <!-- 페이지 제목 -->
    <page-title title="로그인"></page-title>

    <!-- 이메일 입력 -->
    <label for="email" class="label-text">이메일</label>
    <input
      type="email"
      id="email"
      class="input-text"
      name="email"
      v-model="email"
      autocapitalize="none"
    />
    <span class="alert" v-show="valid.email">가입되지 않은 이메일입니다.</span>

    <!-- 비밀번호 입력 -->
    <label for="password" class="label-text">비밀번호</label>
    <input-password
      :password="password"
      id="password"
      @inputVal="updatePassword"
    ></input-password>

    <div class="addition-box">
      <div>
        <input type="checkbox" id="storeId" name="storeId" v-model="storeId" />
        <label for="storeId">아이디 저장</label>
      </div>
      <router-link to="FindPassword">비밀번호 찾기</router-link>
    </div>

    <!-- 로그인 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="login"
    >
      로그인
    </button>

    <!-- SNS 로그인/회원가입 -->
    <div class="subtitle">SNS로 간편 로그인/회원가입하기</div>
    <div class="snslogin">
      <img class="circle" src="@/assets/images/kakao.png" alt="카카오 로그인" />
      <img class="circle" src="@/assets/images/google.png" alt="구글 로그인" />
      <!-- <img src="@/assets/images/kakaologin.png" alt="카카오 로그인" />
      <img src="@/assets/images/googlelogin.png" alt="카카오 로그인" /> -->
    </div>

    <!-- 이메일 가입 버튼 -->
    <div class="subtitle">계정이 없으신가요?</div>
    <button class="btn-white" @click="moveSignUp">이메일로 가입하기</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/Accounts/child/PageTitle.vue";
import InputPassword from "@/components/Accounts/child/InputPassword.vue";

export default defineComponent({
  name: "Login",
  components: {
    PageTitle,
    InputPassword,
  },
  data() {
    return {
      email: "",
      password: "",
      storeId: "",
      valid: {
        email: false,
      },
      isCompleted: false,
    };
  },
  methods: {
    // 비밀번호 컴포넌트에 입력된 텍스트 가져오기
    updatePassword(value: string) {
      this.password = value;
    },
    login() {
      console.log("로그인 처리하기");
    },
    moveSignUp() {
      this.$router.push({ name: "SignUp" });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/reset.scss";
@import "@/assets/css/common.scss";
@import "@/assets/css/accounts.scss";

.addition-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: $size-small;
  font-size: $font-small;
}

.subtitle {
  margin-top: $size-big;
}

.snslogin {
  text-align: center;
  margin-top: $size-medium;
}

img {
  width: 100%;
  cursor: pointer;
  &.circle {
    width: 3.5rem;
    margin: 0 $size-micro;
  }
}
</style>
