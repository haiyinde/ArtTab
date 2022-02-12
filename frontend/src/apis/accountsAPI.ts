import api from "@/http-common";
import LoginInfo from "@/types/LoginInfo";
import NewPassword from "@/types/NewPassword";

class AccountsAPI {
  // 회원가입: DB입력 성공여부에 따라 'success' 또는 'fail' 문자열 반환
  // 회원 등록 후 이메일(인증번호) 전송 프로세스 포함
  signUp(account: LoginInfo) {
    return api.post(`/member/signup`, JSON.stringify(account));
  }

  // 이메일 중복확인: 성공 시 'success', 실패 시(중복이 있는 경우) 오류메시지 반환
  checkEmail(email: string) {
    return api.post(`/member/emailCk`, email);
  }

  // 이메일 (재)전송: 인증번호 변경 후 해당 이메일로 전송, 성공여부에 따라 'success' 또는 'fail' 문자열 반환
  sendEmail(email: string) {
    return api.post(`/member/email`, JSON.stringify({ email: email }));
  }

  // 이메일 인증번호 확인: 성공 시 'success', 실패 시 오류메시지 반환
  checkAuthNum(email: string, id: string) {
    return api.post(
      `/member/authNum`,
      JSON.stringify({ email: email, id: id })
    );
  }

  // 닉네임 중복확인: 성공 시 'success', 실패 시 오류메시지 반환
  checkNickname(nickname: string) {
    return api.post(`/member/idCk`, nickname);
  }

  // 닉네임 등록: 성공여부에 따라 'success' 또는 'fail' 문자열 반환
  addNickname(email: string, nickname: string) {
    return api.post(
      `/member/nickname?nickname=` + nickname,
      JSON.stringify({ email: email })
    );
  }

  // 로그인: Access-token과 로그인 결과 메시지 반환
  login(account: LoginInfo) {
    return api.post(`/member/login`, JSON.stringify(account));
  }

  // 비밀번호 찾기
  findPassword(email: string) {
    return api.put(`/member/password?email=` + email);
  }
  // 회원 정보 조회: 회원 정보(이메일, 닉네임, 소개)를 담은 Token 반환
  // -> get 변경 필요
  getAccount(email: string) {
    return api.get(`/member/me?email=` + email);
  }
  // 프로필 이미지 조회
  // 회원 정보 수정(소개글 수정)
  // 비밀번호 수정: 성공 시 'success' 반환
  updatePassword(email: string, password: NewPassword) {
    return api.put(`/member/me/password`, JSON.stringify({ email, password }));
  }
  // 자기소개 등록, 수정

  // 회원탈퇴(삭제)
  deleteAccount(email: string) {
    return api.delete(`/member/me`, { data: { email: email } });
  }
}

export default new AccountsAPI();
