import { Module } from "vuex";
import { RootState } from "..";
import GalleryArtworkInfo from "@/types/GalleryArtworkInfo";

export interface GalleryStore {
  addedArtworkList: GalleryArtworkInfo[];
}

export const galleryStore: Module<GalleryStore, RootState> = {
  namespaced: true,
  state: {
    addedArtworkList: [],
  },
  getters: {},
  mutations: {
    ADD_NEW_ARTWORK: (state, artwork: GalleryArtworkInfo) => {
      state.addedArtworkList.push(artwork);
    },
    DELETE_ARTWORK_IN_LIST: (state, index: number) => {
      state.addedArtworkList.splice(index, 1);
    },
  },
  actions: {
    addArtwork({ commit }, artwork: GalleryArtworkInfo) {
      commit("ADD_NEW_ARTWORK", artwork);
      // console.log(artwork);
    },
    deleteArtworkInList({ commit }, index: number) {
      commit("DELETE_ARTWORK_IN_LIST", index);
    },
  },
};
