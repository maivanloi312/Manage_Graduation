package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String>{
	List<SinhVien> findByTenSinhVienContaining(String tenSinhVien);
	List<SinhVien> findAllByLopIdLop(String idLop);
	List<SinhVien> findByGPAGreaterThanEqual(Float GPA);
    public SinhVien findByEmailIs(String email);
    @Query("select s from SinhVien s where s.idSinhVien not in (select sinhVien.idSinhVien from DeTai)")
    List<SinhVien> getDSSinhVien();
}
