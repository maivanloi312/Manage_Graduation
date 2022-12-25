package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.shop.domain.GiangVien;

public interface GiangVienService {

	void deleteById(String id);

	Optional<GiangVien> findById(String id);

	List<GiangVien> findAll();

	<S extends GiangVien> S save(S entity);

	List<GiangVien> findByTenGiangVienContaining(String tenGiangVien);
	
	GiangVien findByEmailIs(String email);

}
