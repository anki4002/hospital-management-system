package jsp.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jsp.springboot.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExeptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage(exception.getMessage());
		response.setData("Failure");
		
		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailableException(NoRecordAvailableException exception){
	    
	    ResponseStructure<String> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.NOT_FOUND.value());
	    response.setMessage(exception.getMessage());
	    response.setData("No Data Available");
	    
	    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	
}
