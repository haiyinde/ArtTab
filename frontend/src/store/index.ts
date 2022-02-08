import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import { accountsStore, AccountsState } from "@/store/modules/accountsStore";

export interface RootState {
  AccountsStore: AccountsState;
}

export default createStore({
  modules: { accountsStore },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거(default: localstorage)
      storage: sessionStorage,
    }),
  ],
});
