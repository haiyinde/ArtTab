import { Module } from "vuex";
import { RootState } from "..";
import AccountsAPI from "@/apis/accountsAPI";
import LoginInfo from "@/types/LoginInfo";
import UserInfo from "@/types/UserInfo";
import ResponseData from "@/types/ResponseData";

export interface AccountsState {
  isLogin: boolean;
  isLoginError: boolean;
  joinInfo: LoginInfo | null;
  userInfo: UserInfo | null;
  isConfirmEmail: boolean;
}

export const accountsStore: Module<AccountsState, RootState> = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    joinInfo: null,
    userInfo: null,
    isConfirmEmail: false,
  },
  getters: {
    checkLogin: (state) => {
      return state.isLogin;
    },
    checkUserInfo: (state) => {
      return state.userInfo;
    },
  },
  mutations: {
    SET_JOIN_INFO: (state, joinInfo: LoginInfo) => {
      state.joinInfo = joinInfo;
    },
    SET_IS_LOGIN: (state, isLogin: boolean) => {
      state.isLogin = isLogin;
    },
    SET_USER_INFO: (state, userInfo: UserInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    // 회원가입 가입정보 저장
    storeEmail({ commit }, joinInfo: LoginInfo) {
      commit("SET_JOIN_INFO", joinInfo);
    },
    // 로그인
    async getLogin({ commit }, user: LoginInfo) {
      await AccountsAPI.login(user)
        .then((res: ResponseData) => {
          console.log(res);
          if (res.data) {
            commit("SET_IS_LOGIN", true);
            sessionStorage.setItem("access-token", res.data);
          } else {
            commit("SET_IS_LOGIN", false);
            alert("비밀번호가 틀렸습니다.");
          }
          // 이메일 인증 여부 확인
          // 이메일 인증 안된 사용자이면 joinInfo에 이메일 저장
          // commit("SET_JOIN_EMAIL", "");
          // commit("SET_JOIN_EMAIL", user.email);
        })
        .catch((e) => {
          commit("SET_IS_LOGIN", false);
          console.log(e);
        });
    },
    async getSnsLogin({ commit }, token: string) {
      commit("SET_IS_LOGIN", true);
      sessionStorage.setItem("access-token", token);
    },
    // 사용자 정보 불러오기
    async getUserInfo({ commit }, email: string) {
      await AccountsAPI.getAccount(email).then((res) => {
        commit("SET_USER_INFO", res.data);
      });
    },
    // 로그아웃
    async getLogout({ commit }) {
      commit("SET_USER_INFO", null);
      commit("SET_IS_LOGIN", false);
      sessionStorage.removeItem("access-token");
    },
  },
};
