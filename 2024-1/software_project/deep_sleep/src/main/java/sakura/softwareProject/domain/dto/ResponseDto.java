package sakura.softwareProject.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ResponseDto {

    Object data;
    boolean success;


    public static ResponseDto success(Object data){
        return new ResponseDto(data, true);
    }

    public static ResponseDto fail(Object data){
        return new ResponseDto(data, false);
    }
}
