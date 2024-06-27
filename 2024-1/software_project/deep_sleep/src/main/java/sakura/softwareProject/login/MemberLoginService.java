package sakura.softwareProject.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.dto.MemberDto;
import sakura.softwareProject.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberRepository memberRepository;

    public Member save(MemberDto memberDto){
        Member member = new Member(memberDto);
        if(memberValid(member)){
            memberRepository.save(member);
            return member;
        }
        else{
            return null;
        }

    }


    boolean memberValid(Member member){
        String email = member.getStudentId();
        Optional<Member> bySId = memberRepository.findByStudentId(member.getStudentId());
        Member findMember = bySId.orElse(Member.emptyMember());
        return findMember.getName().equals("empty");
    }
}
