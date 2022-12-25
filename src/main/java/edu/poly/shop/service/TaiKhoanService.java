package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.shop.domain.TaiKhoan;

public interface TaiKhoanService {

	TaiKhoan findByUsernameIs(String username);

	List<TaiKhoan> findByUsernameContaining(String username);

	void deleteById(Long id);

	Optional<TaiKhoan> findById(Long id);

	List<TaiKhoan> findAll();

	<S extends TaiKhoan> S save(S entity);

	List<TaiKhoan> getDSTaiKhoanSinhVien();

	List<TaiKhoan> getDSTaiKhoanGiangVien();

}
