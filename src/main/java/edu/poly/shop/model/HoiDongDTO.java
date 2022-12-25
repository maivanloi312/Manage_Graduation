package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HoiDongDTO {
	private String idTieuBan;
	private String idGiangVien;
	private Boolean isEdit = false;
}
