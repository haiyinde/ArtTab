import api from "@/http-common";
import GalleryArtworkInfo from "@/types/GalleryArtworkInfo";

class GalleryAPI {
  // 갤러리에 추가된 작품 목록 불러오기
  getArtworkList(nickname: string) {
    return api.get(`gallery/${nickname}`);
  }
  // 갤러리 사진 정보 저장하기
  setArtworkList(nickname: string, artworkList: GalleryArtworkInfo[]) {
    return api.post(`gallery/${nickname}`, JSON.stringify(artworkList));
  }
}

export default new GalleryAPI();
