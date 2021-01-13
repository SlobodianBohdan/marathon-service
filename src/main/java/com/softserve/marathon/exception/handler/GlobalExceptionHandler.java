package com.softserve.marathon.exception.handler;


import com.softserve.marathon.exception.CreateException;
import com.softserve.marathon.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    private static final String ERROR = "error";

    @ExceptionHandler({EntityNotFoundException.class})
    public ModelAndView handleEntityNotFoundException(Exception e) {
        log.warn(e.getMessage(), e);
        ModelAndView mav = new ModelAndView("/errorPage");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("errorPage", e.getMessage());
        return mav;
    }

    @ExceptionHandler({CreateException.class})
    public ModelAndView handleCreateException(Exception e) {
        log.warn(e.getMessage(), e);
        ModelAndView mav = new ModelAndView("/errorPage");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("errorPage", e.getMessage());
        return mav;
    }


//    @ExceptionHandler({ConversionNorSupportedException.class, ConversionNorSupportedException.class})
//    public ResponseEntity<Map<String, String>> handleConversionNorSupportedException(Exception e) {
//        log.warn(e.getMessage(), e);
//        Map<String, String> errors = new HashMap<>();
//        errors.put(ERROR, e.getLocalizedMessage());
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(errors);
//    }

}
