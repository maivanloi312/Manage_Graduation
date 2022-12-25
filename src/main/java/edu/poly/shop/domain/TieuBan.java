package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TieuBan")
public class TieuBan implements Serializable{
	@Id @Column(name = "idtieuban", length = 10, columnDefinition = "varchar(10)")
	private String idTieuBan;
	@Column(name = "tentieuban", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenTieuBan;
	@OneToMany(mappedBy = "tieuBan", cascade = CascadeType.ALL)
	List<HoiDong> dsHoiDong;
	@OneToMany(mappedBy = "tieuBanDeTai", cascade = CascadeType.ALL)
	List<DeTai> dsDeTai;
}
