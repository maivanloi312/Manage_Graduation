package edu.poly.shop.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegistrationAccountDTO {
	@NotEmpty(message = "Tên tài khoản không được để trống")
	@Size(min = 3, max = 10, message = "Tên tài khoản phải từ 3-10 kí tự")
	private String username;
	@NotEmpty(message = "Mật khẩu không được để trống")
	@Size(min = 3, max = 10, message = "Mật khẩu phải từ 3-10 kí tự")
	private String password;
	@NotEmpty(message = "Email không được để trống")
	@Email(message = "Email không đúng định dạng")
	private String email;
}
