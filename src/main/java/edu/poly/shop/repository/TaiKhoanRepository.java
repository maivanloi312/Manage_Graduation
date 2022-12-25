package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long>{
	List<TaiKhoan> findByUsernameContaining(String username);
    public TaiKhoan findByUsernameIs(String username);
    @Query("select a from TaiKhoan a where a.quyen.tenQuyen = 'ROLE_AUTHOR'"
	+ "and a.idTaiKhoan not in (select taiKhoanGiangVien.idTaiKhoan from GiangVien where taiKhoanGiangVien.idTaiKhoan <> null )")
    List<TaiKhoan> getDSTaiKhoanGiangVien();
    @Query("select a from TaiKhoan a where a.quyen.tenQuyen = 'ROLE_USER'"
    + "and a.idTaiKhoan not in (select taiKhoanSinhVien.idTaiKhoan from SinhVien where taiKhoanSinhVien.idTaiKhoan <> null)")
    List<TaiKhoan> getDSTaiKhoanSinhVien();
}
