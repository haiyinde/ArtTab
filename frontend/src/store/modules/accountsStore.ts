import { Module } from "vuex";
import { RootState } from "..";
import AccountsAPI from "@/apis/accountsAPI";
import LoginInfo from "@/types/LoginInfo";
import ResponseData from "@/types/ResponseData";

export interface AccountsState {
  isLogin: boolean;
  isLoginError: boolean;
  joinEmail: string;
  userInfo: any;
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
  mutations: {
    SET_JOIN_EMAIL: (state, email: string) => {
      state.joinEmail = email;
    },
    SET_IS_LOGIN: (state, isLogin: boolean) => {
      state.isLogin = isLogin;
    },
  },
  actions: {
    // 회원가입 이메일 저장
    storeEmail({ commit }, email: string) {
      commit("SET_JOIN_EMAIL", email);
    },
    // 로그인
    async getLogin({ commit }, user: LoginInfo) {
      await AccountsAPI.login(user).then((res: ResponseData) => {
        if (res.data.msg === "success") {
          const token = res.data["access-token"];
          commit("SET_IS_LOGIN", true);
          sessionStorage.setItem("access-token", token);
        } else {
          commit("SET_IS_LOGIN", false);
        }
        // 이메일 인증 여부 확인
        // 이메일 인증 안된 사용자이면 joinEmail에 이메일 저장
        commit("SET_JOIN_EMAIL", user.email);
      });
    },
  },
};
