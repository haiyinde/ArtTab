package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class ArtworkListResponseDto implements Comparable<ArtworkListResponseDto>{

    private Long memberId;
    private String memberNickname;
    private Long artworkId; // 작품 식별번호
    private String artworkTitle; // 작품 제목
    private LocalDateTime artworkRegdate; // 작성일
    private String saveFileName; // 서버에 저장된 파일 이름
    private String saveFolder; // 저장된 폴더 경로
    private String imageUrl; // 이미지 url
    private boolean likeOrNot; // 좋아요 여부

    @Builder
    public ArtworkListResponseDto(Long memberId, String memberNickname, Long artworkId, String artworkTitle, LocalDateTime artworkRegdate, String saveFileName, String saveFolder, String imageUrl, boolean likeOrNot) {
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.artworkId = artworkId;
        this.artworkTitle = artworkTitle;
        this.artworkRegdate = artworkRegdate;
        this.saveFileName = saveFileName;
        this.saveFolder = saveFolder;
        this.imageUrl = imageUrl;
        this.likeOrNot = likeOrNot;
    }

    // 정렬용
    @Override
    public int compareTo(ArtworkListResponseDto o) {
        if(this.artworkId>o.artworkId) return -1;
        else return 1;
    }
}
