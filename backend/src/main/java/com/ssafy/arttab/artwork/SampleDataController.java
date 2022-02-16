package com.ssafy.arttab.artwork;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.arttab.artwork.dto.ArtworkFileDto;
import com.ssafy.arttab.artwork.dto.MD5Generator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class SampleDataController {

    @Value("${access.url.location}")
    private String location;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private final ArtworkService artworkService;

    @ApiOperation(value="샘플 데이터 저장하기")
    @PostMapping(path = "api/v1/sample")
    public ResponseEntity<String> getSampleData(@ApiParam("작품을 올릴 회원 아이디") @RequestParam("memberId") Long memberId,
                                                @ApiParam("검색 시작 위치 (최대 1000)") @RequestParam("start") int start,
                                                @ApiParam("가져올 데이터 수 (최대 100개)") @RequestParam("display") int display){

        String clientId = "_oIQiGwYd2N0H2WoGaFP"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "L48PNPL5_2"; //애플리케이션 클라이언트 시크릿값"

        String query = null; // 검색을 원하는 문자열
        try {
            query = URLEncoder.encode("artwork", "UTF-8"); // artwork로 검색한다
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/image?query="+query+"&start="+start+"&display="+display;    // 요청

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders); // 응답 결과

        // 응답 데이터 파싱
        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=(JsonObject) jsonParser.parse(responseBody);
        JsonArray resultArray=(JsonArray) jsonObject.get("items");

        // 디버깅용
        System.out.println("응답 데이터");
        System.out.println(responseBody);

        // 파일 저장할 경로
        String upperPath = "";
        String savePath = "";
        if ("dev".equals(location)){
            upperPath = System.getProperty("user.home") + File.separator + "artwork"; // 프로필 폴더
            savePath = upperPath; // 프로필 사진 주인 이메일
        }else if ("ec2".equals(location)){
            upperPath = System.getProperty("user.dir") + "img";
            savePath = upperPath + File.separator + "artwork"; // artwork의 사용자 디렉토리
        }

        // 가져온 데이터 하나하나에 대해서
        for(int i=0;i<resultArray.size();i++){
            System.out.println(i+"번째 데이터");
            JsonElement element = resultArray.get(i);
            System.out.println(element);
            String title = element.getAsJsonObject().get("title").getAsString();
            String link = element.getAsJsonObject().get("link").getAsString();

            // title의 길이가 너무 길면 99번째 글자까지 잘라준다
            if(title.length()>100){
                title=title.substring(0, 99);
            }

            System.out.println("title: "+title);
            System.out.println("link: "+link);

            try{
                System.out.println("파일 다운로드");
                URL url=new URL(link);
                ReadableByteChannel rbc= Channels.newChannel(url.openStream());

                String saveFilename=new MD5Generator(title+ LocalDateTime.now()) +".jpg"; // 저장될 파일 이름
                String saveFolder = savePath+File.separator+ saveFilename; // 저장될 파일 경로+파일이름

                FileOutputStream fos=new FileOutputStream(saveFolder);// 다운로드 받을 경로와 이름
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 파일을 처음부터 끝까지 다운로드
                fos.close();

                // 파일에 대한 정보 저장
                ArtworkFileDto artworkInfo=ArtworkFileDto.builder()
                        .writerId(memberId)
                        .title(title.substring(0,5))
                        .description(title)
                        .originFileName(saveFilename)
                        .saveFileName(saveFilename)
                        .saveFolder(saveFolder)
                        .build();

                artworkService.save(artworkInfo);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } // 가져온 데이터

        if(responseBody!=null) return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
