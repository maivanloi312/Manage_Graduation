package edu.poly.shop.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeTaiDTO {
	@NotEmpty(message = "Tên đề tài không được đê trống")
	private String tenDeTai;
	private String banMemDoAn;
	private MultipartFile banMemDoAnFile;
	private String sourceCode;
	private MultipartFile sourceCodeFile;
	private String idSinhVien;
	private String idGiangVienHuongDan;
	@Min(value = 0, message = "Điểm phải lớn hơn 0")
	@Max(value = 10, message = "Điểm phải nhỏ hơn 10")
	private Float diemHuongDan;
	private String idGiangVienPhanBien;
	@Min(value = 0, message = "Điểm phải lớn hơn 0")
	@Max(value = 10, message = "Điểm phải nhỏ hơn 10")
	private Float diemPhanBien;
	private String idTieuBan;
	@Min(value = 0, message = "Điểm phải lớn hơn 0")
	@Max(value = 10, message = "Điểm phải nhỏ hơn 10")
	private Float diemTieuBan;
	private Boolean isEdit= false;
}
