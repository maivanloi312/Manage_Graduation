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
@Table(name = "Quyen")

public class Quyen implements Serializable{
	@Id @Column(name = "idquyen")
	private Long idQuyen;
	@Column(name="tenquyen", length = 20, columnDefinition = "nvarchar(20) not null")
	private String tenQuyen;
	@OneToMany(mappedBy = "quyen", cascade = CascadeType.ALL)
	List<TaiKhoan> dsTaiKhoan;
}
