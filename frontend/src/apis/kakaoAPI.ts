import api from "@/http-common";
// import { kakaoApiInstance } from "@/http-common";

class KakaoAPI {
  // 회원가입 여부 확인(로그인)
  getKakaoLogin(code: string) {
    return api.get(`/auth/kakaologin`, { params: { authorize: code } });
  }
  // 토큰 받기
  // getToken(code: string) {
  //   return kakaoApiInstance.post(`/oauth/token`, {
  //     data: {
  //       grant_type: "authorization_code",
  //       client_id: process.env.VUE_APP_KAKAO_REST_KEY,
  //       redirect_uri: process.env.VUE_APP_KAKAO_REDIRECT_URI,
  //       code: code,
  //     },
  //   });
  // }
}

export default new KakaoAPI();
