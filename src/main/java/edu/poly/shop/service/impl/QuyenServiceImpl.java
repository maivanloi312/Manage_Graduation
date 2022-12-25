package edu.poly.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Quyen;
import edu.poly.shop.repository.QuyenRepository;
import edu.poly.shop.service.QuyenService;

@Service
public class QuyenServiceImpl implements QuyenService {
	@Autowired
	QuyenRepository quyenRepository;

	public QuyenServiceImpl(QuyenRepository quyenRepository) {
		this.quyenRepository = quyenRepository;
	}

	@Override
	public List<Quyen> findAll() {
		return quyenRepository.findAll();
	}
	
}
