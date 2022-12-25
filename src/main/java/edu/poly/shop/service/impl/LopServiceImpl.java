package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Lop;
import edu.poly.shop.repository.LopRepository;
import edu.poly.shop.service.LopService;

@Service
public class LopServiceImpl implements LopService{
	@Autowired
	LopRepository lopRepository;

	public LopServiceImpl(LopRepository lopRepository) {
		this.lopRepository = lopRepository;
	}

	@Override
	public List<Lop> findByTenLopContaining(String tenLop) {
		return lopRepository.findByTenLopContaining(tenLop);
	}

	@Override
	public <S extends Lop> S save(S entity) {
		return lopRepository.save(entity);
	}

	@Override
	public List<Lop> findAll() {
		return lopRepository.findAll();
	}

	
	@Override
	public Optional<Lop> findById(String id) {
		return lopRepository.findById(id);
	}


	@Override
	public void deleteById(String id) {
		lopRepository.deleteById(id);
	}

}
