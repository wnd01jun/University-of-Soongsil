package sakura.softwareProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.dto.*;
import sakura.softwareProject.service.MemberService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto signUpDto) {
        SignInResponseDto signInResponseDto = memberService.save(signUpDto);
        ResponseDto responseDto = ResponseDto.success(signInResponseDto);
        return ResponseEntity.created(URI.create("/member/" + signInResponseDto.getId())).body(responseDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto> signIn(@RequestBody SignInRequestDto signInDto) {
        SignInResponseDto signInResponseDto = memberService.signIn(signInDto);
        ResponseDto responseDto = ResponseDto.success(signInResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/test")
    public ResponseEntity<ResponseDto> test(@RequestAttribute("Member") Member member) {
        ResponseDto responseDto = ResponseDto.success(member);
        return ResponseEntity.ok(responseDto);
    }
}
