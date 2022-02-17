import { Module } from "vuex";
import { RootState } from "..";
import GalleryArtworkInfo from "@/types/GalleryArtworkInfo";
import LocAndSizeInfo from "@/types/LocAndSizeInfo";
import GalleryAPI from "@/apis/galleryAPI";
import ResponseData from "@/types/ResponseData";

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
    UPDATE_ARTWORK_LOCATION: (state, loc: LocAndSizeInfo) => {
      state.addedArtworkList[loc.index].xloc = loc.x;
      state.addedArtworkList[loc.index].yloc = loc.y;
    },
    UPDATE_ARTWORK_SIZE: (state, size: LocAndSizeInfo) => {
      state.addedArtworkList[size.index].xloc = size.x;
      state.addedArtworkList[size.index].yloc = size.y;
      state.addedArtworkList[size.index].height = size.h;
      state.addedArtworkList[size.index].weight = size.w;
    },
    DELETE_ARTWORK_IN_LIST: (state, index: number) => {
      state.addedArtworkList.splice(index, 1);
    },
    SET_ADDED_ARTWORK_LIST: (state, artworkList: GalleryArtworkInfo[]) => {
      state.addedArtworkList = artworkList;
    },
  },
  actions: {
    addArtwork({ commit }, artwork: GalleryArtworkInfo) {
      commit("ADD_NEW_ARTWORK", artwork);
      // console.log(artwork);
    },
    updateArtworkLoc({ commit }, loc: LocAndSizeInfo) {
      commit("UPDATE_ARTWORK_LOCATION", loc);
    },
    updateArtworkSize({ commit }, size: LocAndSizeInfo) {
      commit("UPDATE_ARTWORK_SIZE", size);
    },
    deleteArtworkInList({ commit }, index: number) {
      commit("DELETE_ARTWORK_IN_LIST", index);
    },
    getArtworkList({ commit }, nickname: string) {
      GalleryAPI.getArtworkList(nickname).then((res: ResponseData) => {
        console.log(res);
        commit("SET_ADDED_ARTWORK_LIST", res.data);
      });
    },
  },
};
