package edu.poly.shop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DeTai")

public class DeTai implements Serializable{
	@Column(name="tendetai", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenDeTai;
	@Column(name = "banmemdoan",length = 50, columnDefinition = "varchar(50)")
	private String banMemDoAn;
	@Column(name = "sourcecode",length = 50, columnDefinition = "varchar(50)")
	private String sourceCode;
    @Id @OneToOne @JoinColumn(name = "idsinhvien")
    private SinhVien sinhVien;
    @ManyToOne @JoinColumn(name = "idgiangvienhuongdan")
    private GiangVien giangVienHuongDan;
	@Column(name = "diemhuongdan", nullable = true)
	private Float diemHuongDan;
    @ManyToOne @JoinColumn(name = "idgiangvienphanbien")
    private GiangVien giangVienPhanBien;
	@Column(name = "diemphanbien", nullable = true)
	private Float diemPhanBien;
    @ManyToOne @JoinColumn(name = "idtieuban")
    private TieuBan tieuBanDeTai;
	@Column(name = "diemtieuban", nullable = true)
	private Float diemTieuBan;
	@Column(name = "diemtong", nullable = true)
	private Float diemTong;
}
