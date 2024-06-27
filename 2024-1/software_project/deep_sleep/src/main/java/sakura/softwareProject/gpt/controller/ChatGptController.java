package sakura.softwareProject.gpt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sakura.softwareProject.controller.SleepController;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.dto.ResponseDto;
import sakura.softwareProject.domain.dto.SleepAdviceResponseDto;
import sakura.softwareProject.domain.dto.SleepResponseDto;
import sakura.softwareProject.gpt.dto.GptDateRequest;
import sakura.softwareProject.gpt.dto.GptDatesRequest;
import sakura.softwareProject.gpt.service.ChatGptService;
import sakura.softwareProject.repository.MemberRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class ChatGptController {
    private final ChatGptService chatGptService;

    private final MemberRepository memberRepository; // 테스트용

    @GetMapping("/request")
    public ResponseDto request(@RequestAttribute("member")Member member,
                                          @RequestBody GptDateRequest gptDateRequest){
        SleepAdviceResponseDto result = chatGptService.request(member, gptDateRequest);
        if(result == null) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(result);
    }

    @GetMapping("/findAll")
    public ResponseDto findAll(@RequestAttribute("member")Member member){
        List<SleepAdviceResponseDto> all = chatGptService.findAll(member);
        if(all.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(all);
    }

    @GetMapping("/findAllDate")
    public ResponseDto findAllDate(@RequestAttribute("member")Member member){
        List<YearMonth> allDate = chatGptService.findAllDate(member);
        if(allDate.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(allDate);
    }

    @GetMapping("/findByDates")
    public ResponseDto findByDates(@RequestAttribute("member")Member member,
                                   @RequestBody GptDatesRequest dates){
        List<SleepAdviceResponseDto> result = chatGptService.selectByDate(member, dates);
        if(result.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(result);
    }

    @GetMapping("/requestTest")
    public ResponseDto requestTest(@RequestBody GptDateRequest gptDateRequest){
        log.info("okay");
        Member member = memberRepository.findByName("lee");
        log.info("member info = {}, {}, {}", member.getId(), member.getStudentId(), member.getName());
        SleepAdviceResponseDto result = chatGptService.request(member, gptDateRequest);
        if(result == null) return ResponseDto.fail(result);
        return ResponseDto.success(result);
    }


    @GetMapping("/findAllTest")
    public ResponseDto findAll(){
        Member member = memberRepository.findByName("lee");
        List<SleepAdviceResponseDto> all = chatGptService.findAll(member);
        if(all.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(all);
    }

    @GetMapping("/findAllDateTest")
    public ResponseDto findAllDate(){
        Member member = memberRepository.findByName("lee");
        List<YearMonth> allDate = chatGptService.findAllDate(member);
        if(allDate.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(allDate);
    }

    @GetMapping("/findByDatesTest")
    public ResponseDto findByDates(
                                   @RequestBody GptDatesRequest dates){
        Member member = memberRepository.findByName("lee");
        List<SleepAdviceResponseDto> result = chatGptService.selectByDate(member, dates);
        if(result.size() == 0) throw new NoSuchElementException("해당 데이터는 존재하지 않습니다.");
        return ResponseDto.success(result);
    }

    @GetMapping("/requestAll")
    public ResponseDto reqeustAll(@RequestAttribute("member")Member member){
        return ResponseDto.success(chatGptService.requestAll(member));
    }


    //test용



}
