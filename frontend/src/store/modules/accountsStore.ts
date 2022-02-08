import { Module } from "vuex";
import { RootState } from "..";

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
  },
  actions: {
    // 회원가입 이메일 저장
    storeEmail({ commit }, email: string) {
      commit("SET_JOIN_EMAIL", email);
    },
  },
};
