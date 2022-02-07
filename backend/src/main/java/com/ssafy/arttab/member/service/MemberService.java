package com.ssafy.arttab.member.service;

import com.ssafy.arttab.exception.member.DuplicateException;
import com.ssafy.arttab.exception.member.NoSuchMemberExcption;
import com.ssafy.arttab.exception.member.PasswordMismatchException;
import com.ssafy.arttab.member.domain.MailAuth;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.dto.LoginEmail;
import com.ssafy.arttab.member.dto.request.AuthNumCheckRequest;
import com.ssafy.arttab.member.dto.request.MemberSaveRequest;
import com.ssafy.arttab.member.dto.request.IntroUpdateRequest;
import com.ssafy.arttab.member.dto.request.PasswordUpdateRequest;
import com.ssafy.arttab.member.dto.response.MemberInfoResponse;
import com.ssafy.arttab.member.repository.MailAuthRepogitory;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @packageName : com.ssafy.arttab.member
 * @fileName : MemberService
 * @date : 2022-02-03
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final MailSendService mailSendService;
    private final MailAuthRepogitory mailAuthRepogitory;

    /**
     * 회원 등록
     * @param memberSaveRequest
     * @return
     */
    @Transactional
    public boolean saveMember(final MemberSaveRequest memberSaveRequest){
        //중복검사
        MemberEmailCheck(memberSaveRequest.toEntity());


        //비밀번호 암호화
        var password = BCrypt.hashpw(memberSaveRequest.getPassword(),BCrypt.gensalt());

        Member member = Member.builder()
                .email(memberSaveRequest.getEmail())
                .password(password)
                .build();

        try{
            memberRepository.save(member);
            //인증 메일 보내기
            SendNumtoEmail(member.getEmail());
            return true;
        }catch (Exception e){
            return false;
        }



    }

    /**
     * 닉네임 중복 검사
     * @param member
     * @return
     */
    @Transactional(readOnly = true)
    public void MemberIdCheck(final Member member){
        if(memberRepository.existsMembersByNickname(member.getNickname())){
            throw new DuplicateException();
        }

    }

    /**
     * 이메일 중복검사
     * @param member
     * @return
     */
    @Transactional(readOnly = true)
    public void MemberEmailCheck(final Member member){
        if(memberRepository.existsMembersByEmail(member.getEmail())){
            throw new DuplicateException();
        }

    }

    /**
     * 이메일로 인증번호 보내기
     * @param email
     * @return
     */
    public void SendNumtoEmail(final String email){
        // 인증번호 생성
        final String pwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);;

        // DB 확인
        Member member = memberRepository.findMemberByEmail(email);

        Optional<MailAuth> mailAuth = mailAuthRepogitory.findById(member.getId());

        // DB에 있으면 변경, 없으면 등록
        mailAuth.ifPresentOrElse(selectmailAuth ->{
                    selectmailAuth.setId(pwd);
                    mailAuthRepogitory.save(selectmailAuth);
                },() -> {
            var mailAuth1 = MailAuth.builder()
                    .id(pwd)
                    .memNo(member.getId())
                    .build();
            mailAuthRepogitory.save(mailAuth1);}


        );


        // 이메일보내기
        StringBuilder cntnt = new StringBuilder();
        cntnt.append("인증 번호는 ")
                .append(pwd)
                .append( "입니다");
        mailSendService.sendEmail(email, "가입해주셔서  감사힙니다 인증번호를 발급해 드립니다", cntnt.toString());
    }
    public void selectMailAuthId(final AuthNumCheckRequest authNumCheckRequest){
        //이메일로 Id찾기
        var member = memberRepository.findByEmail(authNumCheckRequest.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);

        var mailAuth = mailAuthRepogitory.findById(member.getId())
                .orElseThrow(NoSuchMemberExcption::new);

        //인증번호 맞으면 권한 바꾸기기
       if(mailAuth.getId().equals(authNumCheckRequest.getId())) {
           member.updateAuth();
           mailAuthRepogitory.delete(mailAuth);
       }else{
           throw new PasswordMismatchException();
       }
    }
    /**
     * 닉네임 등록
     * @param loginEmail
     * @param nickname
     */
    @Transactional
    public void addNickname(final LoginEmail loginEmail,final String nickname ){
        var member = memberRepository.findByEmail(loginEmail.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);

        member.updateNickname(nickname);
    }
    /**
     * 회원 조회
     * @param loginEmail
     * @return
     */
    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(final LoginEmail loginEmail){
        var member = memberRepository.findByEmail(loginEmail.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);
        var memberInfoResponse = MemberInfoResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .intro(member.getIntro())
                .build();
        return memberInfoResponse;
    }

    /**
     * 비밀번호 수정
     * @param loginEmail
     * @param passwordUpdateRequest
     */
    @Transactional
    public void updatePassword(final LoginEmail loginEmail, final PasswordUpdateRequest passwordUpdateRequest){
        var member = memberRepository.findByEmail(loginEmail.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);
        if(BCrypt.checkpw(passwordUpdateRequest.getPassword(),member.getPassword())){
            member.updatepassword(BCrypt.hashpw(passwordUpdateRequest.getNewPassword(),BCrypt.gensalt()));
        }else{
            throw new PasswordMismatchException();
        }

    }

    /**
     * 소개글 수정
     * @param loginEmail
     * @param memberUpdateRequest
     */
    @Transactional
    public void updateMember(final LoginEmail loginEmail,final IntroUpdateRequest memberUpdateRequest){
        var member = memberRepository.findByEmail(loginEmail.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);

        member.updateIntro(memberUpdateRequest.getIntro());
    }

    /**
     * 회원 삭제
     * @param loginEmail
     */
    @Transactional
    public void deleteMember(final LoginEmail loginEmail){
        var member = memberRepository.findByEmail(loginEmail.getEmail())
                .orElseThrow(NoSuchMemberExcption::new);

        memberRepository.delete(member);
    }
}
