package sakura.softwareProject.gpt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.service.SleepService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatGptServiceTest {

    @Autowired
    ChatGptService chatGptService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SleepService sleepService;

    @Test
    void requestTest(){
        //given
        Member member = new Member("testMember");

        //when

        //then
    }

}