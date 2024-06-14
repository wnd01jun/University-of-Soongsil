package network.freeTopic.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.Club;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostResponseDto {
    private String title;
    private String content;
    private String author;
    private String club;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

}
