package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.SinhVien;
import edu.poly.shop.repository.SinhVienRepository;
import edu.poly.shop.service.SinhVienService;

@Service
public class SinhVienServiceImpl implements SinhVienService{
	@Autowired
	SinhVienRepository sinhVienRepository;
	
	public SinhVienServiceImpl(SinhVienRepository sinhVienRepository) {
		this.sinhVienRepository = sinhVienRepository;
	}

	@Override
	public List<SinhVien> findByGPAGreaterThanEqual(Float GPA) {
		return sinhVienRepository.findByGPAGreaterThanEqual(GPA);
	}

	@Override
	public List<SinhVien> findByTenSinhVienContaining(String tenSinhVien) {
		return sinhVienRepository.findByTenSinhVienContaining(tenSinhVien);
	}

	@Override
	public List<SinhVien> getDSSinhVien() {
		return sinhVienRepository.getDSSinhVien();
	}

	@Override
	public List<SinhVien> findAllByLopIdLop(String idLop) {
		return sinhVienRepository.findAllByLopIdLop(idLop);
	}

	@Override
	public <S extends SinhVien> S save(S entity) {
		return sinhVienRepository.save(entity);
	}

	@Override
	public List<SinhVien> findAll() {
		return sinhVienRepository.findAll();
	}

	@Override
	public Optional<SinhVien> findById(String id) {
		return sinhVienRepository.findById(id);
	}


	@Override
	public void deleteById(String id) {
		sinhVienRepository.deleteById(id);
	}

	@Override
	public SinhVien findByEmailIs(String email) {
		return sinhVienRepository.findByEmailIs(email);
	}
}
