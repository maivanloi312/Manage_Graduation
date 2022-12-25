package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.poly.shop.domain.TaiKhoan;
import edu.poly.shop.repository.TaiKhoanRepository;
import edu.poly.shop.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	TaiKhoanRepository taiKhoanRepository;
	@Override
	public List<TaiKhoan> findByUsernameContaining(String username) {
		return taiKhoanRepository.findByUsernameContaining(username);
	}
	@Override
	public TaiKhoan findByUsernameIs(String username) {
		return taiKhoanRepository.findByUsernameIs(username);
	}
	@Override
	public <S extends TaiKhoan> S save(S entity) {
		TaiKhoan taiKhoan = findByUsernameIs(entity.getUsername());
		if(taiKhoan != null) {
			if(StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(taiKhoan.getPassword());
			}else {
				entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			} 
		}else {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		}
		return taiKhoanRepository.save(entity);
	}	
	
	@Override
	public List<TaiKhoan> findAll() {
		return taiKhoanRepository.findAll();
	}
	@Override
	public Optional<TaiKhoan> findById(Long id) {
		return taiKhoanRepository.findById(id);
	}
	@Override
	public void deleteById(Long id) {
		taiKhoanRepository.deleteById(id);
	}
	@Override
	public List<TaiKhoan> getDSTaiKhoanGiangVien() {
		return taiKhoanRepository.getDSTaiKhoanGiangVien();
	}
	@Override
	public List<TaiKhoan> getDSTaiKhoanSinhVien() {
		return taiKhoanRepository.getDSTaiKhoanSinhVien();
	}
	
	
}
