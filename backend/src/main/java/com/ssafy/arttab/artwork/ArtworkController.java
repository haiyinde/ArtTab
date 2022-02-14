package com.ssafy.arttab.artwork;

import com.ssafy.arttab.artwork.dto.*;
import com.ssafy.arttab.member.dto.response.MemberInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtworkController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private final ArtworkService artworkService;

    // 작품 등록 api
    @PostMapping(path = "api/v1/artwork")
    public ResponseEntity<String> insert(
            @RequestPart(value = "file", required = true) MultipartFile file,
            @RequestParam("writerId") Long writerId,
            @RequestParam("title") String title,
            @RequestParam("description") String description)
            throws IOException, NoSuchAlgorithmException {

        LocalDateTime time=LocalDateTime.now();
        String originFileName=file.getOriginalFilename();
        String saveFileName=new MD5Generator(originFileName+time+writerId).toString();
        String upperPath=System.getProperty("user.home")+File.separator+"image"; // artwork 디렉토리
//        String savePath=upperPath+File.separator+writerId; // artwork의 사용자 디렉토리
        String savePath=upperPath; // artwork의 사용자 디렉토리

        // 디버깅용
        System.out.println("originFileName: "+originFileName);
        System.out.println(savePath);

        // artwork 디렉토리 없으면 폴더 생성
//        if(!new File(upperPath).exists()){
//            try{
//                new File(upperPath).mkdir();
//            }
//            catch(Exception e){
//                e.getStackTrace();
//            }
//        }

        // 파일이 저장되는 폴더가 없으면 폴더 생성
        if(!new File(savePath).exists()){
            try{
                new File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        String saveFolder=savePath+File.separator+saveFileName;
        file.transferTo(new File(saveFolder)); // 파일 저장

        boolean result = artworkService.save(
                ArtworkFileDto.builder()
                        .writerId(writerId)
                        .title(title)
                        .description(description)
                        .originFileName(originFileName)
                        .saveFileName(saveFileName)
                        .saveFolder(saveFolder)
                        .build()

        );

        if(!result) // result가 false인 경우, 저장 실패한 것.
            return new ResponseEntity<>(FAIL, HttpStatus.OK);

        return new ResponseEntity<>(SUCCESS, HttpStatus.OK); // 저장 성공
    }

    // 작품 수정 api
    @PutMapping("api/v1/artwork/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, // 작품 아이디
                                         @RequestPart(value = "file", required = true) MultipartFile file, // 변경하고 싶은 파일
                                         @RequestParam("writerId") Long writerId, // 작성자 아이디
                                         @RequestParam("title") String title, // 변경하고 싶은 제목
                                         @RequestParam("description") String description) throws IOException, NoSuchAlgorithmException {

        String parentSaveFolder = artworkService.getParentFile(id); // 원래 있던 파일 위치

        File parentFile=new File(parentSaveFolder);
        parentFile.delete(); // 원래 있던 파일 삭제

        LocalDateTime time=LocalDateTime.now();
        String originFileName=file.getOriginalFilename();
        String saveFileName=new MD5Generator(originFileName+time+writerId).toString();
        String upperPath=System.getProperty("user.home")+File.separator+"image"; // artwork 디렉토리
//        String savePath=upperPath+File.separator+writerId; // artwork의 사용자 디렉토리
        String savePath=upperPath; // artwork의 사용자 디렉토리
        String saveFolder=savePath+File.separator+saveFileName;
        file.transferTo(new File(saveFolder)); // 파일 저장

        ArtworkUpdateRequestDto requestDto=ArtworkUpdateRequestDto.builder()
                .title(title)
                .description(description)
                .originFileName(originFileName)
                .saveFileName(saveFileName)
                .saveFolder(saveFolder)
                .build();

        // 엔티티 정보 수정
        Optional<Artwork> artworkResult=artworkService.update(id, requestDto);

        if(artworkResult.isEmpty())
            return new ResponseEntity<>(FAIL, HttpStatus.OK);

        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    // id에 해당하는 작품 조회
    @ApiOperation(value = "id라는 작품 아이디를 가진 작품의 상세 정보를 조회한다.")
    @GetMapping("api/v1/artwork/{id}")
    public ResponseEntity<ArtworkResponseDto> findByNo(@PathVariable("id") Long id,
                                                       @RequestParam("loginId") Long loginId){

        ArtworkResponseDto response=artworkService.findByNo(id, loginId);
        return new ResponseEntity<ArtworkResponseDto>(response, HttpStatus.OK);
    }

    // 작품 전체 조회 api -최근순 정렬
    @ApiOperation(value ="전체 작품을 최근순으로 정렬한다.", notes="내용이 없는 페이지를 요청하면 null이 리턴된다.")
    @GetMapping("api/v1/artwork")
    public ResponseEntity<List<ArtworkListResponseDto>> getArtworkList(@ApiParam("가져올 페이지 (0페이지부터 시작)") @RequestParam("page") int page){
        List<ArtworkListResponseDto> list=artworkService.getArtworkList(page);

        if(list.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 회원이 업로드한 전체 작품 조회 api
    @GetMapping("api/v1/artwork/member")
    public ResponseEntity<List<ArtworkListResponseDto>> getArtworkListByMember(@RequestParam("nickname") String nickname){
        List<ArtworkListResponseDto> list=artworkService.getArtworkByMemberId(nickname);

        if(list.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 작품 삭제 api
    @DeleteMapping("api/v1/artwork/{id}")
    public void deleteArtwork(@PathVariable("id") Long id){

        String parentSaveFolder = artworkService.getParentFile(id); // 원래 있던 파일 위치

        File parentFile=new File(parentSaveFolder);
        parentFile.delete(); // 원래 있던 파일 삭제

        artworkService.delete(id);
    }

    @ApiOperation(value="닉네임에 해당하는 회원이 좋아요 한 작품의 리스트를 리턴한다. 좋아요 한 작품이 없을 경우 null을 리턴한다.")
    @GetMapping("api/v1/artwork/{nickname}/like")
    public ResponseEntity<List<LikeArtworkResponseDto>> getLikeArtworkList(@PathVariable("nickname")String nickname){

        List<LikeArtworkResponseDto> likeList=artworkService.getLikeArtworkList(nickname);
        return new ResponseEntity<>(likeList, HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우한 회원의 작품 목록 조회")
    @GetMapping("api/v1/artwork/{nickname}/follow")
    public ResponseEntity<List<FollowArtworkListResponseDto>> selectFollowArtworkList(@PathVariable("nickname") String nickname){
        List<FollowArtworkListResponseDto> list = artworkService.selectFollowArtworkList(nickname);
        if(list.isEmpty()) return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

