<template>
  <div>조금만 기다려 주세요!!</div>
  <spinner :isLoading="true"></spinner>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import SnsloginAPI from "@/apis/snsloginAPI";
import ResponseData from "@/types/ResponseData";
import Spinner from "@/components/common/Spinner.vue";
import { mapActions } from "vuex";

const accountsStore = "accountsStore";

export default defineComponent({
  name: "KakaoLogin",
  components: {
    Spinner,
  },
  mounted() {
    this.handleRespose();
  },
  methods: {
    ...mapActions(accountsStore, ["getSnsLogin", "getUserInfo", "storeEmail"]),
    // 응답 코드에 따른 분기
    handleRespose() {
      // 카카오 인가코드
      const code = this.$route.query.code;
      const error = this.$route.query.error;
      console.log(code?.toString());

      // 코드가 존재할 경우 사용자가 동의한 것
      if (code) {
        // 회원가입 여부 확인
        SnsloginAPI.getKakaoLogin(code.toString())
          .then((res: ResponseData) => {
            // 가입회원일 때 로그인 처리하기
            if (res.data.result === "success") {
              this.getLogin(res);
            }
            // 미가입 회원일 때 닉네임 등록으로 이동
            else {
              this.storeEmail({ email: res.data.email });
              // 원래 실제 토큰이 들어가야 함
              this.getSnsLogin("token");
              this.$router.push("InsertNickname");
            }
          })
          .catch(() => {
            alert("로그인 처리 중 오류가 발생했습니다.");
          });
        // 가입한 회원이면 로그인 처리, 미가입 회원이면 닉네임 등록으로 이동
      }
      // 에러가 존재할 경우(사용자 미동의, 현재는 이메일이 선택항목이라 작동 안할수도?...)
      else if (error === "consent_required") {
        alert("가입을 위해서는 이메일 제공 동의가 필요합니다.");
        this.$router.push({ name: "Login" });
      }
      // 에러가 존재할 경우(로그인을 취소한 경우)
      else if (error === "access_denied") {
        alert("카카오 로그인이 취소되었습니다.");
        this.$router.push({ name: "Login" });
      }
    },
    async getLogin(res: ResponseData) {
      await this.getSnsLogin(res.data.token);
      await this.getUserInfo(res.data.email);
      this.$router.push({ name: "Main" });
    },
  },
});
</script>

<style lang="scss" scoped>
div {
  text-align: center;
}
</style>
