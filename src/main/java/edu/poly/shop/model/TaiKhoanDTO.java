package edu.poly.shop.model;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaiKhoanDTO {
	private Long idTaiKhoan;
	@Size(min = 3, max = 10, message = "Tên tài khoản phải từ 3-10 kí tự")
	private String username;
	private String password;
	private Boolean isEdit = false;
	private Long idQuyen;
}
