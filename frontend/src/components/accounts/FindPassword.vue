<template>
  <div>
    <!-- 페이지 제목 -->
    <page-title title="비밀번호 찾기"></page-title>

    <!-- 이메일 입력 -->
    <p>
      가입할 때 입력한 이메일 주소를 입력하세요.<br />
      비밀번호는 초기화되어 메일로 전송됩니다.
    </p>
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
    <span class="alert" v-show="valid.email">가입되지 않은 이메일입니다.</span>

    <!-- 이메일 재전송 안내 -->
    <span class="guide"
      ><alert-circle-outline /> 이메일을 받지 못하셨나요?
      <span>이메일 재전송하기</span>
    </span>

    <!-- 이메일 전송 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="sendEmail"
    >
      이메일 전송
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import AccountsAPI from "@/apis/accountsAPI";
import ResponseData from "@/types/ResponseData";
import { validateEmail } from "@/utils/validation";
import { AlertCircleOutline } from "mdue";

export default defineComponent({
  components: {
    PageTitle,
    AlertCircleOutline,
  },
  data() {
    return {
      email: "",
      valid: {
        emailType: false,
        email: false,
      },
      isCompleted: false,
    };
  },
  watch: {
    email: function () {
      this.checkEmail();
    },
  },
  methods: {
    // 이메일 형식 및 중복 검사
    checkEmail() {
      // 이메일 형식 검사
      if (!validateEmail(this.email)) {
        this.valid.emailType = true;
        this.valid.email = false;
        return;
      }
      this.valid.emailType = false;
      // 이메일 가입 여부 검사(회원가입 중복체크와 반대)
      AccountsAPI.checkEmail(this.email)
        .then((res: ResponseData) => {
          if (res.data === "success") {
            this.valid.email = true;
          } else {
            this.valid.email = false;
          }
        })
        .catch(() => {
          this.valid.email = false;
          alert("이메일 중복 확인 중 오류가 발생했습니다.");
        });
    },
    // 이메일 전송
    sendEmail() {
      AccountsAPI.findPassword(this.email)
        .then((res: ResponseData) => {
          if (res.data === "success") {
            alert(
              "메일로 초기화된 비밀번호가 전송되었습니다. 확인 후 로그인해주시기 바랍니다."
            );
            this.$router.push({ name: "Login" });
          } else {
            alert("메일 전송 중 오류가 발생했습니다");
          }
        })
        .catch(() => {
          alert("메일 전송 중 오류가 발생했습니다.");
        });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";

p {
  line-height: 1.5rem;
  margin: $size-regular 0;
}
</style>
