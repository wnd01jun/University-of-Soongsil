package sakura.softwareProject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.domain.dto.*;
import sakura.softwareProject.gpt.controller.ChatGptController;
import sakura.softwareProject.gpt.service.ChatGptService;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.repository.SleepRepository;
import sakura.softwareProject.service.MemberService;
import sakura.softwareProject.service.SleepService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sleep")
@RequiredArgsConstructor
@Slf4j
public class SleepController {
    // sleep data가 어떻게 넘어올지 몰라서 임시로 하나씩 오는걸로 가정하고 작성
    private final SleepService sleepService;


    @GetMapping("/findAll/{memberId}")
    public ResponseDto findAll(@RequestAttribute("member") Member member){
        List<SleepResponseDto> result = sleepService.findAll(member)
                .stream()
                .map(SleepResponseDto::new)
                .collect(Collectors.toList());
        log.info("size = {}", result.size());
        return ResponseDto.success(result);
    }

    @GetMapping("/findByDateRange")
    public ResponseDto findWithDate(@RequestAttribute("member") Member member,
                                               @RequestBody SleepDateRequestDto dto){
        return ResponseDto.success(sleepService.findByDate(member, dto));
    }


    @PostMapping("/saveAll") // 테스트 중
    public ResponseDto saveAll(@RequestAttribute("member") Member member, @RequestBody List<SleepSaveDto> sleepDtos){
        sleepService.saveAll(member, sleepDtos);
        SleepResponseDto dto = sleepService.findRecent(member);

        return ResponseDto.success(dto);
    }

    @GetMapping("/findOne/{date}") // 테스트 중
    public ResponseDto findOne(@RequestAttribute("member") Member member,
                               @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        return ResponseDto.success(sleepService.findByDateOne(member, date));
    }


}
