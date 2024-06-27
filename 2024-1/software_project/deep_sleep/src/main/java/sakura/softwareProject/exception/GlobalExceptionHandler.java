package sakura.softwareProject.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sakura.softwareProject.domain.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleException(Exception e) {
        ResponseDto responseDto = ResponseDto.fail(e.getMessage());
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ResponseDto responseDto = ResponseDto.fail(e.getMessage());
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto> handleBadCredentialsException(BadCredentialsException e) {
        ResponseDto responseDto = ResponseDto.fail(e.getMessage());
        return ResponseEntity.badRequest().body(responseDto);
    }
}
