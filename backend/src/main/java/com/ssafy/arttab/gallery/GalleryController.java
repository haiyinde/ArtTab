package com.ssafy.arttab.gallery;

import com.ssafy.arttab.gallery.dto.GalleryItemListResponseDto;
import com.ssafy.arttab.gallery.dto.GalleryItemSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GalleryController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private final GalleryService galleryService;

    @GetMapping("api/v1/gallery/{nickname}")
    public ResponseEntity<List<GalleryItemListResponseDto>> selectGalleryItemList(@PathVariable("nickname") String nickname){

        List<GalleryItemListResponseDto> list = galleryService.selectGalleryItemList(nickname);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("api/v1/gallery/{nickname}")
    public ResponseEntity<String> insertGalleryItemList(@PathVariable("nickname")String nickname,
                                                        @RequestBody List<GalleryItemSaveRequestDto> list){

        galleryService.deleteGalleryItemAll(nickname); // 회원이 기존에 가지고 있던 갤러리 아이템들 삭제하기

        if(galleryService.insertGalleryItemList(nickname, list)){ // 삽입 성공
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }

        return new ResponseEntity<>(FAIL, HttpStatus.OK); // 삽입 실패
    }

}
