package sakura.softwareProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.SleepAdvice;
import sakura.softwareProject.repository.SleepAdviceRepository;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SleepAdviceService {
    private final SleepAdviceRepository sleepAdviceRepository;

//    public List<SleepAdvice> search(Member member, SleepAdviceRequestDto dto){
//        if(member.getName() == "empty"){
//            throw new NoSuchElementException("해당 멤버는 없는 멤버입니다.");
//        }
//
//        SleepAdviceSearchCondition condition = new SleepAdviceSearchCondition(member, dto);
//        List<SleepAdvice> result = sleepAdviceRepository.search(condition);
//        return result;
//    }

    public SleepAdvice findOne(Member member, YearMonth date){
        return sleepAdviceRepository.findOne(member, date);
    }

    public SleepAdvice save(SleepAdvice sleepAdvice){
        sleepAdviceRepository.save(sleepAdvice);
        return sleepAdvice;
    }

    public List<SleepAdvice> findAll(Member member){
        return sleepAdviceRepository.findAll(member);
    }

    public List<SleepAdvice> selectByDate(Member member, List<YearMonth> dates){
        return sleepAdviceRepository.selectByDate(member, dates);
    }

    public List<YearMonth> findAllDate(Member member){
        return sleepAdviceRepository.findAllDate(member);
    }

}
