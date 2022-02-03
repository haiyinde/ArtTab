package com.ssafy.arttab.artwork;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("작품에 대한 정보를 처리하는 컨트롤러")
@RequestMapping("/artwork")
@RestController
public class ArtworkController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @GetMapping("/list")
    @ApiOperation(value="전체 작품 조회", notes="전체 작품의 리스트를 리턴하는 메소드")
    public ResponseEntity<List<Artwork>> selectAll() throws Exception{
        List<Artwork> list=new ArrayList<>();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value="작품 조회", notes="인자로 받아온 작품번호에 해당하는 작품 정보를 리턴하는 메소드")
    public ResponseEntity<List<Artwork>> selectByArtworkNo(@RequestParam("artwork-id") int id) throws Exception{
        List<Artwork> result=new ArrayList<>();
        result.add(new Artwork());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value="작품 저장", notes="작품에 대한 정보를 저장하는 메소드. db 삭제 성공 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.")
    public ResponseEntity<String> insertArtwork(@RequestBody Artwork art) throws Exception{
        // 저장에 성공하는 경우
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/{artId}/{price}")
    @ApiOperation(value="작품 가격 수정", notes="첫번째 인자로 들어온 아이디에 해당하는 작품의 가격을 두번째 인자로 들어온 가격으로 수정하는 메소드")
    public ResponseEntity<String> updatePrice(@PathVariable int artId, @PathVariable int price) throws Exception{
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistNo}")
    @ApiOperation(value="작가 작품 조회", notes="인자로 들어온 작가번호를 가진 작가가 그린 그림을 모두 리턴하는 메소드")
    public ResponseEntity<List<Artwork>> selectByArtistId(@PathVariable int artistNo) throws Exception{
        List<Artwork> list=new ArrayList<Artwork>();
        return new ResponseEntity<List<Artwork>>(list, HttpStatus.OK);
    }

    @GetMapping("/owner")
    @ApiOperation(value="소유자 작품 조회", notes="회원이 소유한 작품을 모두 리턴하는 메소드")
    public ResponseEntity<List<Artwork>> selectByOwner() throws Exception{
        List<Artwork> list=new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/like")
    @ApiOperation(value="회원이 '좋아요'한 작품", notes="회원이 좋아요 한 작품의 리스트를 보여주는 메소드")
    public ResponseEntity<List<Artwork>> likeList(@RequestParam("member_no")int memberNo){
        List<Artwork> list=new ArrayList<>();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/like")
    @ApiOperation(value="작품 좋아요", notes="첫번째 인자로 들어온 회원번호의 회원이 두번째 인자로 들어온 작품번호의 작품을 좋아요 했다는 처리를 해주는 메소드")
    public ResponseEntity<String> likeArtwork(@RequestParam("member_no") int memberNo, @RequestParam("artwork_no") int artworkNo) throws Exception{
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/like")
    @ApiOperation(value="작품 좋아요 취소", notes="첫번째 인자로 들어온 회원번호의 회원이 두번째 인자로 들어온 작품번호의 작품을 좋아요 취소했다는 처리를 해주는 메소드")
    public ResponseEntity<String> unlikeArtwork(@RequestParam("member_no") int memberNo, @RequestParam("artwork_no") int artworkNo) throws Exception{
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    // 작품 거래 이력
    @GetMapping("/dealList")
    @ApiOperation(value="작품 거래 이력 리스트 조회", notes="인자로 들어온 작품번호에 해당하는 작품의 거래이력을 가져오는 메소드")
    public ResponseEntity<List<ArtworkDeal>> selectArtworkDeal(){
        List<ArtworkDeal> list=new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/deal")
    @ApiOperation(value="작품 거래 이력 등록", notes="작품 거래 이력을 등록하는 메소드")
    public ResponseEntity<String> insertArtworkDeal(){
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }



}
