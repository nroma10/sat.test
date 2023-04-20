package sat.recruitment.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@NotBlank(message = "The name is required")
	public String name;

	@NotBlank(message = "The email is required")
	@Email(message = "The email format is invalid")
	public String email;

	@NotNull(message = "The address is required")
	public String address;

	@NotBlank(message = "The phone is required")
	public String phone;

	@NotBlank(message = "The user type is required")
	@Pattern(regexp = "Premium|Normal|SuperUser", message = "Possible values for user_type are: Normal, Premium, or SuperUser")
	@JsonProperty("user_type")
	public String userType;

	@NotNull(message = "The money is required")
	public Double money;

	@Override
	public String toString() {
		return new StringJoiner(",")
				.add(name)
				.add(email)
				.add(phone)
				.add(address)
				.add(userType)
				.add(String.valueOf(money))
				.toString();
	}
}
