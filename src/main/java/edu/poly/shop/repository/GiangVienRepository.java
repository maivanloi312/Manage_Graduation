package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String>{
	List<GiangVien> findByTenGiangVienContaining(String tenGiangVien);
    public GiangVien findByEmailIs(String email);
}
