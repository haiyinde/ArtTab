package com.ssafy.arttab.artwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// 회원이 팔로우하는 회원들의 작품 리스트를 리턴하기 위한 dto
@Setter
@NoArgsConstructor
@ApiModel(value="팔로우 회원 작품 리스트 dto", description = "회원이 팔로우하는 회원들의 작품 리스트를 리턴하는 형식")
public class FollowArtworkListResponseDto implements Comparable<FollowArtworkListResponseDto>{

    @ApiModelProperty(value = "작품 저장 경로")
    private String saveFolder;
    @ApiModelProperty(value = "팔로우한 회원의 작품 수")
    private int artworkNum;
    @ApiModelProperty(value = "팔로우한 회원의 전체 팔로워 수")
    private int followerNum;
    @ApiModelProperty(value = "작품 아이디")
    private int artworkId;
    @ApiModelProperty(value = "회원 이메일")
    private String memberMail;
    private int followNum;

    @Builder
    public FollowArtworkListResponseDto(String saveFolder, int artworkNum, int followerNum, int artworkId, String memberMail, int followNum) {
        this.saveFolder = saveFolder;
        this.artworkNum = artworkNum;
        this.followerNum = followerNum;
        this.artworkId = artworkId;
        this.memberMail = memberMail;
        this.followNum = followNum;
    }

    @Override
    public int compareTo(FollowArtworkListResponseDto o) {
        return o.followNum-this.followNum;
    }
}
