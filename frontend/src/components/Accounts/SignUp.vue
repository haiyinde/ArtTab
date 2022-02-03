<template>
  <div>
    <!-- 페이지 제목 -->
    <page-title title="회원가입" step="1" total="3"></page-title>

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
    <span class="alert" v-show="valid.emailType"
      >이메일 형식이 올바르지 않습니다.</span
    >
    <span class="alert" v-show="valid.email"
      >이미 가입된 이메일 주소입니다.</span
    >

    <!-- 비밀번호 입력 -->
    <label for="password" class="label-text">비밀번호</label>
    <input-password
      :password="password"
      :placetext="'영문, 특수문자 포함 8자리 이상'"
      id="password"
      @inputVal="updatePassword"
    ></input-password>
    <span class="alert" v-show="valid.password"
      >영문, 특수문자 포함 8자리 이상 입력해주세요.</span
    >

    <!-- 비밀번호 확인 -->
    <label for="checkPwd" class="label-text">비밀번호 확인</label>
    <input-password
      :password="checkPwd"
      :placetext="'비밀번호를 다시 입력해주세요.'"
      id="checkPwd"
      @inputVal="updatecheckPwd"
    ></input-password>
    <span class="alert" v-show="valid.checkPwd"
      >비밀번호가 일치하지 않습니다.</span
    >

    <!-- 개인정보 및 이용약관 동의 -->
    <div class="agree-box">
      <input type="checkbox" id="agree" name="agree" v-model="agree" />
      <label for="agree">개인정보 및 이용약관에 동의합니다.</label>
    </div>

    <!-- 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="moveConfirmEmail"
    >
      다음
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/Accounts/child/PageTitle.vue";
import InputPassword from "@/components/Accounts/child/InputPassword.vue";
import PV from "password-validator"; // 비밀번호 유효성 검사 라이브러리

export default defineComponent({
  name: "SignUp",
  components: {
    PageTitle,
    InputPassword,
  },
  data() {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      checkPwd: "",
      isShowPwd: false,
      agree: "",
      valid: {
        emailType: false,
        email: false,
        password: false,
        checkPwd: false,
      },
      isCompleted: false,
    };
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
    email: function () {
      this.checkForm();
    },
    password: function () {
      this.checkForm();
    },
    checkPwd: function () {
      this.checkForm();
    },
    agree: function () {
      this.checkForm();
    },
  },
  methods: {
    // 비밀번호 컴포넌트에 입력된 텍스트 가져오기
    updatePassword(value: string) {
      this.password = value;
    },
    updatecheckPwd(value: string) {
      this.checkPwd = value;
    },
    // 회원가입 폼 전체 유효성 검사 -> 완료 시 버튼 활성화
    checkForm() {
      if (this.validatePassword() && this.checkPassword() && this.agree) {
        this.isCompleted = true;
        return;
      }
      this.isCompleted = false;
    },
    // 비밀번호 유효성 검사
    validatePassword(): boolean {
      if (!this.passwordSchema.validate(this.password)) {
        this.valid.password = true;
        return false;
      }
      this.valid.password = false;
      return true;
    },
    // 비밀번호와 비밀번호 확인 입력값의 일치 여부 체크
    checkPassword(): boolean {
      if (this.password !== this.checkPwd) {
        this.valid.checkPwd = true;
        return false;
      }
      this.valid.checkPwd = false;
      return true;
    },
    // 다음 단계(이메일 인증)로 이동
    moveConfirmEmail() {
      this.$router.push({ name: "ConfirmEmail" });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/reset.scss";
@import "@/assets/css/common.scss";
@import "@/assets/css/accounts.scss";

.agree-box {
  text-align: center;
  margin: $size-big 0 $size-regular;
}
</style>
