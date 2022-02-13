import api from "@/http-common";
import LikeInfo from "@/types/LikeInfo";

class LikesAPI {
  // 좋아요 등록
  addLike(likeInfo: LikeInfo) {
    return api.post(`/like`, JSON.stringify(likeInfo));
  }
  // 좋아요 취소
  deleteLike(likeInfo: LikeInfo) {
    return api.delete(`/like`, {
      params: likeInfo,
    });
  }
}

export default new LikesAPI();
