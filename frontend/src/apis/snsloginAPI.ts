import api from "@/http-common";

class SnsloginAPI {
  // 카카오회원가입 여부 확인(로그인)
  getKakaoLogin(code: string) {
    return api.get(`/auth/kakaologin`, { params: { authorize: code } });
  }
  // 구글 회원가입 여부 확인(로그인)
  getGoogleLogin(code: string) {
    return api.get(`/auth/googlelogin`, { params: { authorize: code } });
  }
}

export default new SnsloginAPI();
