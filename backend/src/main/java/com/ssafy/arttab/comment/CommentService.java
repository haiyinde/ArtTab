package com.ssafy.arttab.comment;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.comment.dto.CommentListResponseDto;
import com.ssafy.arttab.comment.dto.CommentSaveRequestDto;
import com.ssafy.arttab.comment.dto.CommentUpdateRequestDto;
import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;

    @Value("${access.url.artworks}")
    private String artworkImgUrl;

    @Value("${access.url.profiles}")
    private String profileImgUrl;

    @Value("${access.url.profileDefault}")
    private String profileDefaultImgUrl;

    @Transactional
    public void insert(Long id, CommentSaveRequestDto requestDto) {
        // 작품 객체값 유무
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        // 회원 객체값 유무
       Member member = memberRepository.findById(requestDto.getMemberId())
               .orElseThrow(IllegalArgumentException::new);

        commentRepository.save(requestDto.toEntity(artwork, member));
    }

    @Transactional
    public List<CommentListResponseDto> findAllDesc(Long id) {
        List<Comment> commetList = commentRepository.findAllDesc(id)
                .orElseThrow(IllegalArgumentException::new);
        List<CommentListResponseDto> responseDtos = new ArrayList<>();

        for (Comment comment : commetList) {
            Member member = memberRepository.findById(comment.getMember().getId())
                    .orElseThrow(NoSuchMemberException::new);
            String imgUrl = "";
            if ("default.jpg".equals(member.getSaveFilename())){
                imgUrl = profileDefaultImgUrl + member.getSaveFilename();
            }else {
                imgUrl = profileImgUrl + member.getSaveFilename();
            }
            responseDtos.add(new CommentListResponseDto(comment, member, imgUrl));
        }
        return responseDtos;
    }

    @Transactional
    public void update(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        comment.update(requestDto.getContent());
    }

    @Transactional
    public void delete(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + commentId));
        commentRepository.delete(comment);
    }

}
