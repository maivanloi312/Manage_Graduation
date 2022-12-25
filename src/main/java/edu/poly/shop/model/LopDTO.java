package edu.poly.shop.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LopDTO {
	@Size(max = 10, message = "Mã không được quá 10 kí tự")
	@NotEmpty(message = "Mã không được để trống")
	private String idLop;
	@Size(max = 20, message = "Tên không được quá 20 kí tự")
	@NotEmpty(message = "Tên không được để trống")
	private String tenLop;
	@Size(max = 20, message = "Tên không được quá 20 kí tự")
	@NotEmpty(message = "Tên không được để trống")
	private String tenHeDaoTao;
	@Size(max = 20, message = "Tên không được quá 20 kí tự")
	@NotEmpty(message = "Tên không được để trống")
	private String tenKhoaDaoTao;
	private Boolean isEdit = false;
}
