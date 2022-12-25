package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.shop.domain.SinhVien;

public interface SinhVienService {

	void deleteById(String id);

	Optional<SinhVien> findById(String id);

	List<SinhVien> findAll();

	<S extends SinhVien> S save(S entity);

	List<SinhVien> findAllByLopIdLop(String idLop);

	List<SinhVien> findByTenSinhVienContaining(String tenSinhVien);

	List<SinhVien> findByGPAGreaterThanEqual(Float GPA);
	
	SinhVien findByEmailIs(String email);

	List<SinhVien> getDSSinhVien();
	

}
