package sakura.softwareProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.domain.dto.*;
import sakura.softwareProject.domain.enums.SleepStatus;
import sakura.softwareProject.repository.MemberRepository;
import sakura.softwareProject.repository.SleepRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SleepService {
    private final MemberRepository memberRepository;
    private final SleepRepository sleepRepository;


    public List<Sleep> findAll(Member member){
        return sleepRepository.findAll(member);
    }

    public List<SleepResponseDto> findByDate(Member member, SleepDateRequestDto dto){
        List<Sleep> sleeps = sleepRepository.findByDate(member, dto);
        List<SleepResponseDto> result = sleeps.stream()
                .map(SleepResponseDto::new)
                .toList();
        if(result.size() == 0) return null;
        return result;
    }

    public SleepResponseDto findByDateOne(Member member, LocalDate date){

        Sleep sleep = sleepRepository.findByDateOne(member, date);
        return SleepResponseDto.makeDto(sleep);
    }

    public SleepResponseDto findRecent(Member member){
        return SleepResponseDto.makeDto(sleepRepository.findRecent(member));
    }

    public void saveAll(Member member2, List<SleepSaveDto> sleeps){
//        Member member = findMember(memberId);
//        sleepDtos.stream()
//                .filter(sleepDto -> sleepRepository.checkExist(member, sleepDto))
//                .map(Sleep::new)
//                .forEach(sleepRepository::save);
        Member member = memberRepository.findById(member2.getId()).get();
        LocalDate lastUpdated = member.getLastUpdated();
        List<SleepSaveDto> result;
        if(lastUpdated != null) {
            result = sleeps.stream()
                    .filter(sleepSaveDto -> sleepSaveDto.getDate().isAfter(lastUpdated))
                    .sorted(Comparator.comparing(SleepSaveDto::getDate))
                    .toList();
        }
        else{
            result = sleeps.stream()
                    .sorted(Comparator.comparing(SleepSaveDto::getDate))
                    .toList();
        }

        for (SleepSaveDto dto : result) {
            List<SleepDto> sleepDtos2 = dto.getSleeps();
            int inBed, asleep, awake, remSleep, deepSleep, coreSleep;
            inBed = asleep = awake = remSleep = deepSleep = coreSleep = 0;
            LocalDateTime startDate, endDate;
            List<SleepDto> sleepDtos = sleepDtos2.stream()
                    .filter(sleepDto -> sleepDto.getSleepStatus() != SleepStatus.ASLEEP
                            && sleepDto.getSleepStatus() != SleepStatus.IN_BED)
                    .sorted(Comparator.comparing(SleepDto::getStartDate))
                    .toList();
            log.info("date = {}, size = {} =======",dto.getDate(), sleepDtos.size());
            if(sleepDtos.size() == 0){
                continue;
            }

            startDate = sleepDtos.get(0).getStartDate();
            endDate = sleepDtos.get(sleepDtos.size()-1).getEndDate();
            for (SleepDto sleepDto : sleepDtos) {
                long minute = ChronoUnit.MINUTES.between(sleepDto.getStartDate(), sleepDto.getEndDate());
                log.info("startDate = {}, endDate = {}, minute = {}", sleepDto.getStartDate(), sleepDto.getEndDate(), minute);
                switch(sleepDto.getSleepStatus()){
                    case AWAKE -> awake+= minute;
                    case IN_BED -> inBed += minute;
                    case ASLEEP -> asleep += minute;
                    case REM_SLEEP -> remSleep += minute;
                    case DEEP_SLEEP -> deepSleep += minute;
                    case CORE_SLEEP -> coreSleep += minute;
                }
            }
            log.info("awake = {}", awake);
            Sleep sleep = new Sleep(member, inBed, asleep, awake, remSleep, deepSleep, coreSleep, dto.getDate(),
            startDate, endDate);
            if(member.getLastUpdated() == null || member.getLastUpdated().isBefore(dto.getDate())){
                member.setLastUpdated(dto.getDate());
            }
            sleepRepository.save(sleep);
        }
    }

    public List<YearMonth> findMonthOver5(Member member){
        return sleepRepository.findMonthOver5(member);
    }

//    private Integer saveOne(SleepSaveDto dto){
//
//    }


    private Member findMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElse(Member.emptyMember());
        if(member.getName() == "empty"){
            throw new NoSuchElementException("해당 멤버는 존재하지 않는 멤버입니다.");
        }
        return member;
    }

}
