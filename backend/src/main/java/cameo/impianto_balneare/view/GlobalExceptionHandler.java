package cameo.impianto_balneare.view;

import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public interface GlobalExceptionHandler {
    @ExceptionHandler(SchedulerException.class)
    default ResponseEntity<String> handleSchedulerException(SchedulerException exception) {
        return ResponseEntity.badRequest().body("Scheduler Error: " + exception.getMessage());
    }
}
