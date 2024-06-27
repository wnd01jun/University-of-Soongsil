package sakura.softwareProject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.dto.SignInRequestDto;
import sakura.softwareProject.domain.dto.SignInResponseDto;
import sakura.softwareProject.domain.dto.SignUpRequestDto;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.security.JwtProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignInResponseDto save(SignUpRequestDto signUpRequestDto){
        Optional<Member> existMember = memberRepository.findByStudentId(signUpRequestDto.getStudentId());
        if(existMember.isPresent()){
            throw new DataIntegrityViolationException("이미 존재하는 아이디입니다.");
        }
        // encrypt password.
        String encryptedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        // set member.
        Member member = new Member(signUpRequestDto.getName(),
                    signUpRequestDto.getStudentId(),
                    encryptedPassword);
        // save member.
        memberRepository.save(member);

        // set sign in.
        SignInRequestDto signInRequestDto =
                new SignInRequestDto(
                        signUpRequestDto.getStudentId(),
                        signUpRequestDto.getPassword()
                        );
        return signIn(signInRequestDto);
    }

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto){
        Optional<Member> member = memberRepository.findByStudentId(signInRequestDto.getStudentId());
        if(member.isPresent()){
            if(passwordEncoder.matches(signInRequestDto.getPassword(), member.get().getPassword())){
                return new SignInResponseDto(
                                jwtProvider.createToken(member.get()),
                                member.get().getStudentId(),
                                member.get().getId()
                        );
            }
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        } else {
            throw new BadCredentialsException("아이디가 존재하지 않습니다.");
        }
    }
}
