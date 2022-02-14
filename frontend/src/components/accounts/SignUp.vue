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
      v-model="account.email"
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
      :password="account.password"
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
  <spinner :isLoading="isLoading"></spinner>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import InputPassword from "@/components/accounts/child/InputPassword.vue";
import { validateEmail } from "@/utils/validation";
import AccountsAPI from "@/apis/accountsAPI";
import ResponseData from "@/types/ResponseData";
import Spinner from "@/components/common/Spinner.vue";
import PV from "password-validator"; // 비밀번호 유효성 검사 라이브러리
import LoginInfo from "@/types/LoginInfo";
import { mapActions } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "SignUp",
  components: {
    PageTitle,
    InputPassword,
    Spinner,
  },
  data() {
    return {
      account: {
        email: "",
        password: "",
      } as LoginInfo,
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
      isLoading: false,
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
    "account.email": function () {
      this.checkEmail();
    },
    "account.password": function () {
      this.validatePassword();
    },
    checkPwd: function () {
      this.checkPassword();
    },
    agree: function () {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions(accountsStore, ["storeEmail"]),
    // 비밀번호 컴포넌트에 입력된 텍스트 가져오기
    updatePassword(value: string) {
      this.account.password = value;
    },
    updatecheckPwd(value: string) {
      this.checkPwd = value;
    },
    // 회원가입 폼 전체 유효성 검사 -> 완료 시 버튼 활성화
    checkForm() {
      if (
        !this.valid.emailType &&
        !this.valid.email &&
        !this.valid.password &&
        !this.valid.checkPwd &&
        this.agree
      ) {
        this.isCompleted = true;
        return;
      }
      this.isCompleted = false;
    },
    // 이메일 형식 및 중복 검사
    async checkEmail() {
      // 이메일 형식 검사
      if (!validateEmail(this.account.email)) {
        this.valid.emailType = true;
        this.valid.email = false;
        return;
      }
      this.valid.emailType = false;
      // 이메일 중복 검사
      await AccountsAPI.checkEmail(this.account.email)
        .then((res: ResponseData) => {
          if (res.data === "success") {
            this.valid.email = false;
          } else {
            this.valid.email = true;
          }
        })
        .catch((e) => {
          console.log(e);
        });
      if (!this.valid.email) {
        this.checkForm();
      }
    },
    // 비밀번호 유효성 검사
    validatePassword() {
      if (!this.passwordSchema.validate(this.account.password)) {
        this.valid.password = true;
        return;
      }
      this.valid.password = false;
      this.checkForm();
    },
    // 비밀번호와 비밀번호 확인 입력값의 일치 여부 체크
    checkPassword() {
      if (this.account.password !== this.checkPwd) {
        this.valid.checkPwd = true;
        return;
      }
      this.valid.checkPwd = false;
      this.checkForm();
    },
    // 가입 처리 후 다음 단계(이메일 인증)로 이동
    async moveConfirmEmail() {
      // 회원가입 처리
      this.startSpinner();
      await AccountsAPI.signUp(this.account)
        .then((res: ResponseData) => {
          if (res.data === "success") {
            // store에 이메일 저장 및 메일 전송 후 다음 단계 페이지로 이동
            this.storeEmail(this.account.email);

            this.endSpinner();
            this.$router.push({
              name: "ConfirmEmail",
            });
          } else {
            this.endSpinner();
            alert("가입 중 오류가 발생했습니다. 다시 시도해주시기 바랍니다.");
          }
        })
        .catch(() => {
          this.endSpinner();
          alert("가입 중 오류가 발생했습니다. 다시 시도해주시기 바랍니다.");
        });
    },
    startSpinner() {
      this.isLoading = true;
    },
    endSpinner() {
      this.isLoading = false;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";

.agree-box {
  text-align: center;
  margin: $size-big 0 $size-regular;
}
</style>
