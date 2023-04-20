package sat.recruitment.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sat.recruitment.api.exception.UserAlreadyExistsException;
import sat.recruitment.api.model.User;
import sat.recruitment.api.service.UserService;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		try {
			userService.createUser(user);
			return ResponseEntity.ok().build();
		} catch (UserAlreadyExistsException userAlreadyExistsException) {
			return ResponseEntity.badRequest().body(userAlreadyExistsException.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
