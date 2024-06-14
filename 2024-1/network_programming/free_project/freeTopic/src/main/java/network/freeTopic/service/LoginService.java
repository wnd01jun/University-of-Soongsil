package network.freeTopic.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.consts.SessionConst;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.form.RegisterForm;
import network.freeTopic.repository.ClubRepository;
import network.freeTopic.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberClubService memberClubService;
    public Member login(String loginId, String password){
        Member member = memberRepository.findByStudentId(loginId);
        if(member == null) return null;
        if(passwordEncoder.matches(password, member.getPassword())){
            return member;
        }
        else{
            return null;
        }



        //세션 처리는 컨트롤러에게 위임
    }

    public Member register(RegisterForm registerForm) {
        if (memberRepository.findByStudentId(registerForm.getStudentId()) != null) {
            return null;
        }

        Member member = new Member(registerForm, passwordEncoder.encode(registerForm.getPassword()));
        memberRepository.save(member);
        memberClubService.createInit(member);

        return member;
    }
}
