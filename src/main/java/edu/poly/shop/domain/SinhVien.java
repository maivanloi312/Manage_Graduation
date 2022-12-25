package edu.poly.shop.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SinhVien")
public class SinhVien implements Serializable{
	@Id @Column(name = "idsinhvien", length = 10, columnDefinition = "varchar(10)")
	private String idSinhVien;
	@Column(name = "tensinhvien", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenSinhVien;
	@Column(name = "gioitinh", length = 20, columnDefinition = "nvarchar(20) not null")
	private String gioiTinh;
	@Column(name = "email", unique = true, length = 20, columnDefinition = "varchar(20) not null")
	private String email;
	@Column(name= "gpa", nullable = false)
	private Float GPA;
	@ManyToOne @JoinColumn(name = "idlop")
	private Lop lop;
    @OneToOne @JoinColumn(name = "idtaikhoan")
    private TaiKhoan taiKhoanSinhVien;
    @OneToOne(mappedBy = "sinhVien", cascade = CascadeType.ALL)
    private DeTai deTai;
}