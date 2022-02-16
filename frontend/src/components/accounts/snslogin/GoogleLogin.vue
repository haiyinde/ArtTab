<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import SnsloginAPI from "@/apis/snsloginAPI";
import ResponseData from "@/types/ResponseData";

export default defineComponent({
  name: "GoogleLogin",
  mounted() {
    this.handleRespose();
  },
  methods: {
    // 응답 코드에 따른 분기
    async handleRespose() {
      // 구글 인가코드
      const code = this.$route.query.code;
      console.log(code?.toString());

      // 코드가 존재할 경우 사용자가 동의한 것
      if (code) {
        // 회원가입 여부 확인
        SnsloginAPI.getGoogleLogin(code.toString()).then(
          (res: ResponseData) => {
            console.log(res);
          }
        );
        // 가입한 회원이면 로그인 처리, 미가입 회원이면 닉네임 등록으로 이동
      } else {
        console.log("인증코드 못 받아옴");
      }
    },
    // 회원가입 여부 확인
    // 가입한 회원이면 로그인 처리
    // 가입하지 않은 회원이면 등록 후 닉네임 등록으로 이동
  },
});
</script>

<style lang="scss" scoped></style>
