package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {
	List<Lop> findByTenLopContaining(String tenLop);
}
