package ee.priit.pall.homemade.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestError {
    private String message;
    private ErrorCode code;
}


