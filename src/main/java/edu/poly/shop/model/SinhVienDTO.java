package edu.poly.shop.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SinhVienDTO {
	@Size(max = 10, message = "Mã không được quá 10 kí tự")
	@NotEmpty(message = "Mã không được để trống")
	private String idSinhVien;
	@Size(max = 20, message = "Tên không được quá 20 kí tự")
	@NotEmpty(message = "Tên không được để trống")
	private String tenSinhVien;
	private String gioiTinh;
	@Size(max = 20, message = "Email không được quá 20 kí tự")
	@NotEmpty(message = "Email không được để trống")
	private String email;
	@NotNull(message = "Điểm không được để trống") 
	@Min(value = 0, message = "Điểm phải lớn hơn 0")
	@Max(value = 10, message = "Điểm phải nhỏ hơn 10")
	private Float GPA;
	private String idLop;
	private Long idTaiKhoan;
	private Boolean isEdit = false;
}
