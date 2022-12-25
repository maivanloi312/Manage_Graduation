package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.shop.domain.TieuBan;

public interface TieuBanService {

	void deleteById(String id);

	Optional<TieuBan> findById(String id);

	List<TieuBan> findAll();

	<S extends TieuBan> S save(S entity);

	List<TieuBan> findByTenTieuBanContaining(String tenTieuBan);



}
