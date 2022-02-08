<!-- 이메일 인증 페이지
      - 새로고침마다 메일이 전송되는 것을 막기 위해 mounted로 메일을 전송하지 않았음
      - 전 페이지에서 이동 전 메일 발송 필요함
      - [참고] 회원 등록 API에는 이메일 전송 프로세스가 포함되어 있음
-->

<template>
  <div>
    <page-title title="이메일 인증하기" step="2" total="3"></page-title>

    <!-- 인증코드 입력 -->
    <p>
      이메일로 전송된 인증코드를 입력해주세요.<br />
      이메일 인증 후 그림 감상과 등록이 가능합니다.
    </p>
    <input
      type="text"
      id="authNum"
      class="input-text"
      name="authNum"
      v-model="authNum"
      placeholder="인증코드 입력"
      autocapitalize="none"
    />
    <span class="alert" v-show="valid.authNum"
      >인증코드가 일치하지 않습니다.</span
    >

    <!-- 이메일 재전송 안내 -->
    <span class="guide"
      ><alert-circle-outline /> 이메일을 받지 못하셨나요?
      <span @click="sendEmail">이메일 재전송하기</span>
    </span>

    <!-- 버튼 -->
    <button
      :class="{ disabled: !isCompleted }"
      :disabled="!isCompleted"
      @click="moveInsertNickname"
    >
      다음
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PageTitle from "@/components/accounts/child/PageTitle.vue";
import { AlertCircleOutline } from "mdue";
import AccountsAPI from "@/apis/accountsAPI";
import ResponseData from "@/types/ResponseData";
import { mapState } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "ConfirmEmail",
  components: {
    PageTitle,
    AlertCircleOutline,
  },
  data() {
    return {
      authNum: "",
      valid: {
        authNum: false,
      },
      isCompleted: false,
    };
  },
  computed: {
    ...mapState(accountsStore, ["joinEmail"]),
  },
  watch: {
    authNum: function () {
      this.checkAuthNum();
    },
  },
  methods: {
    // 이메일 전송
    sendEmail() {
      AccountsAPI.sendEmail(this.joinEmail).then((res: ResponseData) => {
        if (res.data === "success") {
          alert("이메일이 정상적으로 전송되었습니다.");
        } else {
          alert(
            "이메일 전송 중 오류가 발생했습니다. 이메일 재전송 버튼을 통해 다시 시도해주시기 바랍니다."
          );
        }
      });
    },
    // 이메일 인증 처리 추가
    checkAuthNum() {
      AccountsAPI.checkAuthNum(this.joinEmail, this.authNum).then(
        (res: ResponseData) => {
          if (res.data === "success") {
            this.valid.authNum = false;
            this.isCompleted = true;
          }
          this.valid.authNum = true;
          this.isCompleted = false;

          // 이메일 인증 기능 확인 후 삭제해야할 코드
          this.isCompleted = true;
        }
      );
    },
    // 다음 단계(닉네입 입력)로 이동
    moveInsertNickname() {
      this.$router.push({
        name: "InsertNickname",
      });
    },
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/accounts.scss";
</style>
