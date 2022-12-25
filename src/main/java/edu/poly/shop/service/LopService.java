package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.shop.domain.Lop;

public interface LopService {


	void deleteById(String id);

	Optional<Lop> findById(String id);

	List<Lop> findAll();

	<S extends Lop> S save(S entity);

	List<Lop> findByTenLopContaining(String tenLop);

}
