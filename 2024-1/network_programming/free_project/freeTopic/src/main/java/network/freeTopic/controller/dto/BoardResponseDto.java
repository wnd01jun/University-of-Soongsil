package network.freeTopic.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDto {
    private Long id;

    private String title;

    private String name;
    private String studentId;
    private String clubName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;



}
