package sakura.softwareProject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.dto.SignInRequestDto;
import sakura.softwareProject.domain.dto.SignInResponseDto;
import sakura.softwareProject.domain.dto.SignUpRequestDto;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.security.JwtProvider;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void saveTest(){
        Member member = new Member("kim", "1234@naver.com", "1234");
        memberRepository.save(member);
        assertThat(memberRepository.findById(member.getId()).get()).isEqualTo(member);
    }

    @Test
    @DisplayName("login test: default")
    public void loginTest() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("20213041", "1234", "dummy_name");
        SignInResponseDto responseDto1 = memberService.save(signUpRequestDto);

        SignInRequestDto requestDto = new SignInRequestDto("20213041", "1234");
        SignInResponseDto responseDto2 = memberService.signIn(requestDto);
        assertThat(jwtProvider.getId(responseDto2.getToken())).isEqualTo(responseDto1.getId().toString());
    }

    @Test
    @DisplayName("login exception test: 아이디가 존재하지 않습니다.")
    public void loginTestForIdNotExistException() {
        SignInRequestDto requestDto= new SignInRequestDto("00000001", "1234");
        boolean isCaught = false;
        try {
            memberService.signIn(requestDto);
        } catch (BadCredentialsException e) {
            assertThat(e.getMessage()).isEqualTo("아이디가 존재하지 않습니다.");
            isCaught = true;
        }
        assertThat(isCaught).isTrue();
    }

    @Test
    @DisplayName("login exception test: 비밀번호가 일치하지 않습니다.")
    public void loginTestForPasswordNotMatchException() {
        SignUpRequestDto requestDto = new SignUpRequestDto("00000002", "1234", "dummy_name");
        memberService.save(requestDto);
        SignInRequestDto requestDto2 = new SignInRequestDto("00000002", "5678");
        boolean isCaught = false;
        try {
            memberService.signIn(requestDto2);
        } catch (BadCredentialsException e) {
            assertThat(e.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
            isCaught = true;
        }
        assertThat(isCaught).isTrue();
    }

    @Test
    @DisplayName("sign-up exception test: 이미 존재하는 아이디입니다.")
    public void signUpTestForExistStudentId() {
        SignUpRequestDto requestDto1 = new SignUpRequestDto("12345678", "1234", "dummy_name");
        memberService.save(requestDto1);
        SignUpRequestDto requestDto2 = new SignUpRequestDto("12345678", "1234", "bum-boo");
        boolean isCaught = false;
        try {
            memberService.save(requestDto2);
        } catch (DataIntegrityViolationException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
            isCaught = true;
        }
        assertThat(isCaught).isTrue();
    }
}
