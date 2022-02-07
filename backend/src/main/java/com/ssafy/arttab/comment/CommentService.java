package com.ssafy.arttab.comment;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.comment.dto.CommentListResponseDto;
import com.ssafy.arttab.comment.dto.CommentSaveRequestDto;
import com.ssafy.arttab.comment.dto.CommentUpdateRequestDto;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long insert(Long id, CommentSaveRequestDto requestDto) {
        // 작품 객체값 유무
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        // 회원 객체값 유무
       Member member = memberRepository.findById(requestDto.getMemberId())
               .orElseThrow(IllegalArgumentException::new);

        return commentRepository.save(requestDto.toEntity(artwork, member)).getId();
    }

    @Transactional
    public List<CommentListResponseDto> findAllDesc(Long id) {
        return commentRepository.findAllDesc(id).stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        comment.update(requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + commentId));
        commentRepository.delete(comment);
    }

}
