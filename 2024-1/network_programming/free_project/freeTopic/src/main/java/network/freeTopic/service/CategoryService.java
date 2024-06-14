package network.freeTopic.service;

import com.querydsl.core.types.PredicateOperation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Category;
import network.freeTopic.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void categoryInit(){
        Category study = Category.builder()
                .name("학술")
                .description("학술 카테고리입니다..")
                .build();
        Category exercise = Category.builder()
                .name("운동")
                .description("운동 카테고리입니다..")
                .build();
        Category cook = Category.builder()
                .name("요리")
                .description("요리 카테고리입니다.")
                .build();
        Category develop = Category.builder()
                .name("개발")
                .description("개발 카테고리입니다.")
                .build();
        Category travel = Category.builder()
                .name("여행")
                .description("여행 카테고리입니다.")
                .build();
        categoryRepository.save(study);
        categoryRepository.save(exercise);
        categoryRepository.save(cook);
        categoryRepository.save(develop);
        categoryRepository.save(travel);
        return;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
