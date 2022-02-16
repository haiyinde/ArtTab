import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import { accountsStore, AccountsState } from "@/store/modules/accountsStore";
import { galleryStore, GalleryStore } from "@/store/modules/galleryStore";

export interface RootState {
  AccountsStore: AccountsState;
  GalleryStore: GalleryStore;
}

export default createStore({
  modules: { accountsStore, galleryStore },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거(default: localstorage)
      storage: sessionStorage,
    }),
  ],
});
