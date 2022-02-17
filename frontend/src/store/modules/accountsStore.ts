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
    isConfirmEmail: true,
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
    SET_IS_LOGIN_ERROR: (state, isLoginError: boolean) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo: UserInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_IS_CONFIRM_EMAIL: (state, isConfirmed: boolean) => {
      state.isConfirmEmail = isConfirmed;
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
          console.log(res.data);
          if (res.data === "fail") {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
          // 이메일 인증되지 않은 사람일 때
          else if (res.data === "NoauthorizedMember") {
            commit("SET_JOIN_INFO", user);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_CONFIRM_EMAIL", false);
          }
          // 비밀번호 틀린것 등 fail일 때
          else {
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", res.data);
          }
        })
        .catch(() => {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
          alert("로그인 중 오류가 발생했습니다.");
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
