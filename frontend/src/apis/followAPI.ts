import api from "@/http-common";
import FollowInfo from "@/types/FollowInfo";

class FollowAPI {
  // 좋아요 등록
  addFollow(followInfo: FollowInfo) {
    return api.post(`/follow`, JSON.stringify(followInfo));
  }
  // 좋아요 취소
  deleteFollow(followInfo: FollowInfo) {
    return api.delete(`/follow`, {
      params: followInfo,
    });
  }
}

export default new FollowAPI();
