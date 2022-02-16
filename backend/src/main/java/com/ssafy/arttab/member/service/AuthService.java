package com.ssafy.arttab.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.arttab.config.JWTUtil;
import com.ssafy.arttab.exception.authorization.NoauthorizedMemberException;
import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @packageName : com.ssafy.arttab.member.service
 * @fileName : AuthService
 * @date : 2022-02-15
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    @Value("${custom.kakao.redir}")
    private String redir_URL;
    /**
     * 카카오 토큰 얻기
     * @param authorize_code
     * @return
     */
    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";


        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=e5f705d09d7a71f3b86015b0d85d239c");
            sb.append("&redirect_uri="+redir_URL);
            sb.append("&client_secret=PoJM7yFUxYQVp2gZJrlU3GGWt6ibuHNA");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoauthorizedMemberException();
        }

        return access_Token;
    }

    /**
     * 카카오 회원 정보 얻기
     * @param access_Token
     * @return
     */
    public HashMap<String, String> getUserInfo(String access_Token) {

        HashMap<String, String> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String id = element.getAsJsonObject().get("id").getAsString();
            String email = null;
            if (kakao_account.getAsJsonObject().get("email") != null) {
                email = kakao_account.getAsJsonObject().get("email").getAsString();
                userInfo.put("id", id);
                userInfo.put("email", email);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }
    //기존회원 확인
    public Map checkUser(HashMap<String, String> userInfo){
        Map message = new HashMap();
        String email = userInfo.get("email");
        //랜덤 비밀번호
        final String pwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        //비밀번호 암호화
        final String password = BCrypt.hashpw(pwd,BCrypt.gensalt());

        // 프로필 사진 기본 이미지로 설정
        final String defaultSaveFolder=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"default.jpg";

        // DB에 있으면 토큰 보내기, 없으면 등록
        if(memberRepository.existsMembersByEmail(email)){
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(NoSuchMemberException::new);
            Map payload = new HashMap();
            payload.put("id",member.getId());
            message.put("result","success");
            message.put("email",email);
            message.put("token",jwtUtil.createToken(payload));

               }else{
            Member member =Member.builder()
                    .email(email)
                    .password(password)
                    .saveFolder(defaultSaveFolder)
                    .saveFilename("default.jpg")
                    .auth(1)
                    .build();
            memberRepository.save(member);
            message.put("result","fail");
            message.put("email",email);

        }
        return message;
    }

}
