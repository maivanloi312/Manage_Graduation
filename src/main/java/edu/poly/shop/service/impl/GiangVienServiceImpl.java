package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.GiangVien;
import edu.poly.shop.repository.GiangVienRepository;
import edu.poly.shop.service.GiangVienService;

@Service
public class GiangVienServiceImpl implements GiangVienService{
	@Autowired
	GiangVienRepository giangVienRepository;
	
	public GiangVienServiceImpl(GiangVienRepository giangVienRepository) {
		this.giangVienRepository = giangVienRepository;
	}

	@Override
	public List<GiangVien> findByTenGiangVienContaining(String tenGiangVien) {
		return giangVienRepository.findByTenGiangVienContaining(tenGiangVien);
	}

	@Override
	public <S extends GiangVien> S save(S entity) {
		return giangVienRepository.save(entity);
	}

	@Override
	public List<GiangVien> findAll() {
		return giangVienRepository.findAll();
	}

	@Override
	public Optional<GiangVien> findById(String id) {
		return giangVienRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		giangVienRepository.deleteById(id);
	}
	
	@Override
	public GiangVien findByEmailIs(String email) {
		return giangVienRepository.findByEmailIs(email);
	}

}
