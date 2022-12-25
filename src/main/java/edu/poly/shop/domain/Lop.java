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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Lop")
public class Lop implements Serializable{
	@Id @Column(name = "idlop", length = 10, columnDefinition = "varchar(10)")
	private String idLop;
	@Column(name = "tenlop", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenLop;
	@Column(name="tenhedaotao", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenHeDaoTao;
	@Column(name = "tenkhoadaotao", length = 20, columnDefinition = "varchar(20) not null" )
	private String tenKhoaDaoTao;
	@OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
	List<SinhVien> dsSinhVien;
}
