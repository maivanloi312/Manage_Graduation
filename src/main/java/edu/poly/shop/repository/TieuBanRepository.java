package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.TieuBan;

@Repository
public interface TieuBanRepository extends JpaRepository<TieuBan, String>{
	List<TieuBan> findByTenTieuBanContaining(String tenTieuBan);
}
