<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import SnsloginAPI from "@/apis/snsloginAPI";
import ResponseData from "@/types/ResponseData";

export default defineComponent({
  name: "KakaoLogin",
  mounted() {
    this.handleRespose();
  },
  methods: {
    // 응답 코드에 따른 분기
    async handleRespose() {
      // 카카오 인가코드
      const code = this.$route.query.code;
      const error = this.$route.query.error;
      console.log(code?.toString());

      // 코드가 존재할 경우 사용자가 동의한 것
      if (code) {
        // 회원가입 여부 확인
        SnsloginAPI.getKakaoLogin(code.toString()).then((res: ResponseData) => {
          console.log(res);
        });
        // 가입한 회원이면 로그인 처리, 미가입 회원이면 닉네임 등록으로 이동
      }
      // 에러가 존재할 경우(사용자 미동의, 현재는 이메일이 선택항목이라 작동 안할수도?...)
      else if (error === "consent_required") {
        alert("가입을 위해서는 이메일 제공 동의가 필요합니다.");
        this.$router.push("Login");
      }
      // 에러가 존재할 경우(로그인을 취소한 경우)
      else if (error === "access_denied") {
        alert("카카오 로그인이 취소되었습니다.");
        this.$router.push("Login");
      }
    },
    // 회원가입 여부 확인
    // 가입한 회원이면 로그인 처리
    // 가입하지 않은 회원이면 등록 후 닉네임 등록으로 이동
  },
});
</script>

<style lang="scss" scoped></style>
