package sakura.softwareProject.gpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.SleepAdvice;
import sakura.softwareProject.domain.dto.SleepAdviceResponseDto;
import sakura.softwareProject.domain.dto.SleepDateRequestDto;
import sakura.softwareProject.domain.dto.SleepResponseDto;
import sakura.softwareProject.gpt.dto.*;
import sakura.softwareProject.service.SleepAdviceService;
import sakura.softwareProject.service.SleepService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatGptService {

    private final SleepAdviceService sleepAdviceService;

    private final SleepService sleepService;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;
    public SleepAdviceResponseDto request(Member member, GptDateRequest gptDateRequest){
        YearMonth yearMonth = gptDateRequest.getDate();
        LocalDate nowDate = LocalDate.now();
        log.info("inRequest!");
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        log.info("firstDay = {}, now = {}",firstDay, nowDate);
        if(lastDay.isAfter(nowDate)){
            return null; // 최소 다음 월이 되어야 사용할 수 있다;
        }// 테스트를 위해 잠시 off

        SleepAdvice one = sleepAdviceService.findOne(member, yearMonth);
        if(one != null){
            return new SleepAdviceResponseDto(one); // 이미 해당 월의 어드바이스가 존재하면 그 데이터를 리턴한다.
        }


        SleepDateRequestDto requestDto = new SleepDateRequestDto(firstDay, lastDay);
        List<SleepResponseDto> result = sleepService.findByDate(member, requestDto);
        if(result.size() < 5){ // 데이터가 5개 미만이면 돌릴 수 없다.
            return null;
        }
        PromptDto prompt = makePrompt(result);
        log.info("prompt = {}", prompt);
        String advice = gptService(prompt.getPrompt());
//        String advice = "good";
        log.info("advice = {}", advice);

        SleepAdvice sleepAdvice = new SleepAdvice(member, yearMonth, advice
                , prompt.getAvgTotalSleep(), prompt.getAvgAwakeSleep());
        log.info("avgAwakeSleep = {}", prompt.getAvgAwakeSleep());
        SleepAdvice save = sleepAdviceService.save(sleepAdvice);

        if(save == null) return null;

        SleepAdviceResponseDto returnDto = new SleepAdviceResponseDto(sleepAdvice);

        return returnDto;
    }
    public List<SleepAdviceResponseDto> findAll(Member member){
        return sleepAdviceService.findAll(member)
                .stream()
                .map(SleepAdviceResponseDto::new)
                .sorted(Comparator.comparing(SleepAdviceResponseDto::getDate))
                .toList();
    }

    public List<SleepAdviceResponseDto> selectByDate(Member member, GptDatesRequest dates){
        return sleepAdviceService.selectByDate(member, dates.getDates())
                .stream()
                .map(SleepAdviceResponseDto::new)
                .sorted(Comparator.comparing(SleepAdviceResponseDto::getDate))
                .toList();
    }

    public List<YearMonth> findAllDate(Member member){
        return sleepAdviceService.findAllDate(member);
    }

    private PromptDto makePrompt(List<SleepResponseDto> list){
        int totalSleep, totalInBed;
        totalSleep = totalInBed = 0;
        double awakeTime = 0;

        for (SleepResponseDto dto : list) {
            totalSleep += dto.getTotalSleepTime().getHour() * 60;
            totalSleep += dto.getTotalSleepTime().getMinute();
            totalInBed += dto.getTotalInBedTime().getHour() * 60;
            totalInBed += dto.getTotalInBedTime().getMinute();
            awakeTime += dto.getAwake().getMinute() + dto.getAwake().getHour() * 60;
        }
        double avgSleep = 0, avgInBed = 0;
        avgSleep = (double)totalSleep / list.size();
        avgInBed = (double)totalInBed / list.size();
        awakeTime = awakeTime / list.size();
        StringBuffer result = new StringBuffer("이 사람은 한달 평균 " + avgSleep + " 분을 잤고, 그 중, " + awakeTime
                + "분을 잠에서 자던 도중에 깼어 그리고 잠자리에 든 시각은 각");
        for (SleepResponseDto dto : list) {
            result.append(", " + dto.getStartDate().toString());
        }
        result.append("이고 완전히 수면이 끝난 시각은 각");
        for (SleepResponseDto dto : list) {
            result.append(", " + dto.getEndDate().toString());
        }
        result.append("일 때 주어진 데이터들을 토대로 사용자의 수면을 분석해줘");

        String prompt = new String(result);
        log.info("prompt = {}", prompt);
        log.info("awakeTime = {}", awakeTime);
        PromptDto promptDto = new PromptDto(prompt, LocalTime.of((int) avgSleep / 60, (int) avgSleep % 60)
                , LocalTime.of((int) awakeTime / 60, (int) awakeTime % 60));
        return promptDto;
    }

    private String gptService(String prompt){
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }

    public List<SleepAdviceResponseDto> requestAll(Member member){
        return sleepService.findMonthOver5(member).stream()
                .map(GptDateRequest::new)
                .sorted(Comparator.comparing(GptDateRequest::getDate))
                .map(gptDateRequest -> request(member, gptDateRequest))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
