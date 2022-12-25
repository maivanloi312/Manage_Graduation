package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.TieuBan;
import edu.poly.shop.repository.TieuBanRepository;
import edu.poly.shop.service.TieuBanService;

@Service
public class TieuBanServiceImpl implements TieuBanService{
	@Autowired
	TieuBanRepository tieuBanRepository;

	public TieuBanServiceImpl(TieuBanRepository tieuBanRepository) {
		this.tieuBanRepository = tieuBanRepository;
	}

	@Override
	public List<TieuBan> findByTenTieuBanContaining(String tenTieuBan) {
		return tieuBanRepository.findByTenTieuBanContaining(tenTieuBan);
	}

	@Override
	public <S extends TieuBan> S save(S entity) {
		return tieuBanRepository.save(entity);
	}

	@Override
	public List<TieuBan> findAll() {
		return tieuBanRepository.findAll();
	}


	@Override
	public Optional<TieuBan> findById(String id) {
		return tieuBanRepository.findById(id);
	}


	@Override
	public void deleteById(String id) {
		tieuBanRepository.deleteById(id);
	}

}
