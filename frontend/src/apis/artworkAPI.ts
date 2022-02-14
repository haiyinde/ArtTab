import api from "@/http-common";
import { fileApiInstance } from "@/http-common";

class ArtworkAPI {
  // 작품 등록 API(artwork: file, title, writerId, description)
  addArtwork(artwork: FormData) {
    return fileApiInstance.post(`/artwork`, artwork);
  }

  // 작품 수정 API
  update(
    desc: string,
    file: File,
    id: number,
    title: string,
    writerId: number
  ) {
    return api.put(
      `/artwork/` +
        id +
        `?desc=` +
        desc +
        `&title=` +
        title +
        `&writerId=` +
        writerId
    );
  }
  // id에 해당하는 작품 조회 (개별 작품 조회)
  getArtworkById(id: number, loginId: number) {
    return api.get(`/artwork/${id}`, { params: { loginId: loginId } });
  }
  // 작품 전체 조회 API
  getArtworkList(page: number) {
    return api.get(`/artwork?page=` + page);
  }
  // 회원이 업로드한 전체 작품 조회 API
  getArtworkListByMember(nickname: string) {
    return api.get(`/artwork/member?nickname=` + nickname);
  }
  // 작품 삭제 API
  deleteArtwork(id: number) {
    return api.delete(`/artwork/` + id);
  }
}

export default new ArtworkAPI();
