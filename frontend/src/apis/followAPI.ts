import api from "@/http-common";
import FollowInfo from "@/types/FollowInfo";

class FollowAPI {
  // 팔로우 등록
  addFollow(followInfo: FollowInfo) {
    return api.post(`/follow`, JSON.stringify(followInfo));
  }
  // 팔로우 취소
  deleteFollow(followInfo: FollowInfo) {
    return api.delete(`/follow`, { data: followInfo });
  }
  // 모든 팔로잉 목록 조회
  getAllFollowing(id: number) {
    return api.get(`following/${id}`);
  }
}

export default new FollowAPI();
