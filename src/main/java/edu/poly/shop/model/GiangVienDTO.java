package edu.poly.shop.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GiangVienDTO {
	@Size(max = 10, message = "Mã không được quá 10 kí tự")
	@NotEmpty(message = "Mã không được để trống")
	private String idGiangVien;
	@Size(max = 20, message = "Tên không được quá 20 kí tự")
	@NotEmpty(message = "Tên không được để trống")
	private String tenGiangVien;
	private String gioiTinh;
	@Size(max = 20, message = "Email không được quá 20 kí tự")
	@NotEmpty(message = "Email không được để trống")
	private String email;
	private Long idTaiKhoan;
	private Boolean isEdit = false;
}
