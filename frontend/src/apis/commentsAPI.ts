import api from "@/http-common";
import CommentSave from "@/types/CommentSave";
import CommentUpdate from "@/types/CommentUpdate";

class CommentsApI {
  // 댓글 전체 조회(id: 작품id)
  getComments(id: number) {
    return api.get(`/artwork/${id}/comment`);
  }

  // 댓글 등록(id: 작품id)
  addComment(id: number, comment: CommentSave) {
    return api.post(`/artwork/${id}/comment`, JSON.stringify(comment));
  }

  // 댓글 수정(id: 댓글id)
  updateComment(id: number, comment: CommentUpdate) {
    return api.put(`/comment/${id}`, JSON.stringify(comment));
  }

  // 댓글 삭제(id: 댓글id)
  deleteComment(id: number) {
    return api.delete(`/comment/${id}`);
  }
}

export default new CommentsApI();
