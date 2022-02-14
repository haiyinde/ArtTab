import { Module } from "vuex";
import { RootState } from "..";
import AccountsAPI from "@/apis/accountsAPI";
import LoginInfo from "@/types/LoginInfo";
import UserInfo from "@/types/UserInfo";
import ResponseData from "@/types/ResponseData";

export interface AccountsState {
  isLogin: boolean;
  isLoginError: boolean;
  joinEmail: string;
  userInfo: UserInfo | null;
  isConfirmEmail: boolean;
}

export const accountsStore: Module<AccountsState, RootState> = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    joinEmail: "",
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
    SET_JOIN_EMAIL: (state, email: string) => {
      state.joinEmail = email;
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
    // 회원가입 이메일 저장
    storeEmail({ commit }, email: string) {
      commit("SET_JOIN_EMAIL", email);
    },
    // 로그인
    async getLogin({ commit }, user: LoginInfo) {
      await AccountsAPI.login(user)
        .then((res: ResponseData) => {
          if (res.data) {
            commit("SET_IS_LOGIN", true);
            sessionStorage.setItem("access-token", res.data);
          } else {
            commit("SET_IS_LOGIN", false);
            alert("비밀번호가 틀렸습니다.");
          }
          // 이메일 인증 여부 확인
          // 이메일 인증 안된 사용자이면 joinEmail에 이메일 저장
          // commit("SET_JOIN_EMAIL", "");
          // commit("SET_JOIN_EMAIL", user.email);
        })
        .catch(() => {
          commit("SET_IS_LOGIN", false);
          alert("비밀번호가 틀렸습니다.");
        });
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
