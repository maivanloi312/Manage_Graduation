package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GiangVien")
public class GiangVien implements Serializable{
	@Id @Column(name = "idgiangvien", length = 10, columnDefinition = "varchar(10)")
	private String idGiangVien;
	@Column(name = "tengiangvien", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenGiangVien;
	@Column(name = "gioitinh", length = 20, columnDefinition = "nvarchar(20) not null")
	private String gioiTinh = "Nam";
	@Column(name = "email", unique = true, length = 20, columnDefinition = "varchar(20) not null")
	private String email;
	@OneToMany(mappedBy = "giangVienHuongDan", cascade = CascadeType.ALL)
	List<DeTai> dsDeTaiHuongDan;
	@OneToMany(mappedBy = "giangVienPhanBien", cascade = CascadeType.ALL)
	List<DeTai> dsDeTaiPhanBien;
	@OneToMany(mappedBy = "giangVien", cascade = CascadeType.ALL)
	List<HoiDong> dsHoiDong;
    @OneToOne @JoinColumn(name = "idtaikhoan")
    private TaiKhoan taiKhoanGiangVien;
}
