package edu.poly.shop.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoiDong")
public class HoiDong implements Serializable {
	@Id @ManyToOne @JoinColumn(name = "idtieuban")
	private TieuBan tieuBan;
	@Id @ManyToOne @JoinColumn(name = "idgiangvien")
	private GiangVien giangVien;
}
