package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuyenDTO{
	private Long idQuyen;
	private String tenQuyen;
	private Boolean isEdit = false;
}
