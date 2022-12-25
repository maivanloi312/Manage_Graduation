package edu.poly.shop.controller.admin;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.util.StringUtils;

import edu.poly.shop.domain.HoiDong;
import edu.poly.shop.domain.TieuBan;
import edu.poly.shop.domain.GiangVien;
import edu.poly.shop.model.GiangVienDTO;
import edu.poly.shop.model.HoiDongDTO;
import edu.poly.shop.model.TieuBanDTO;
import edu.poly.shop.service.GiangVienService;
import edu.poly.shop.service.TieuBanService;

@Controller
@RequestMapping("admin/dshoidong")
public class HoiDongController {
	@Autowired
	EntityManagerFactory entityManagerFactory;
	@Autowired
	GiangVienService giangVienService;
	@Autowired
	TieuBanService tieuBanService;
	public HoiDong getHoiDongByIdTieuBanIdGiangVien(String idTieuBan, String idGiangVien) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from HoiDong t where t.giangVien.idGiangVien = :idGiangVien"
					+ "and  t.tieuBan.idTieuBan = :idTieuBan");
			query.setParameter("idGiangVien", idGiangVien);
			query.setParameter("idTieuBan", idTieuBan);
			HoiDong entity = (HoiDong) query.list().get(0);
			if(entity != null) {
				return entity;
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		return null;
	}
	@ModelAttribute("dsgiangvien")
	public List<GiangVienDTO> getDSGiangVien(){
		return giangVienService.findAll().stream().map(item->{
			GiangVienDTO dto = new GiangVienDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	@ModelAttribute("dstieuban")
	public List<TieuBanDTO> getDSTieuBan(){
		return tieuBanService.findAll().stream().map(item->{
			TieuBanDTO dto = new TieuBanDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}	
	@GetMapping("add")
	public String add(Model model) {
		HoiDongDTO dto = new HoiDongDTO();
		dto.setIsEdit(false);
		model.addAttribute("hoidong", dto);
		return "admin/dshoidong/addOrEdit";
	}
	@GetMapping("edit/{idTieuBan}/{idGiangVien}")
	public ModelAndView edit(ModelMap model, 
			@PathVariable("idTieuBan") String idTieuBan,
			@PathVariable("idGiangVien") String idGiangVien) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from HoiDong t where t.tieuBan.idTieuBan = :idTieuBan and t.giangVien.idGiangVien = :idGiangVien");
			query.setParameter("idTieuBan", idTieuBan);
			query.setParameter("idGiangVien", idGiangVien);
			HoiDong entity = (HoiDong) query.list().get(0);
			HoiDongDTO dto = new HoiDongDTO();
			if(entity != null) {
				BeanUtils.copyProperties(entity, dto);
				dto.setIdTieuBan(entity.getTieuBan().getIdTieuBan());
				dto.setIdGiangVien(entity.getGiangVien().getIdGiangVien());
				dto.setIsEdit(true);
				model.addAttribute("hoidong", dto);
				return new ModelAndView("admin/dshoidong/addOrEdit", model);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message", "Hội đồng không tồn tại");
		return new ModelAndView("forward:/admin/dshoidong", model);
	}
	@GetMapping("delete/{idTieuBan}/{idGiangVien}")
	public ModelAndView delete(ModelMap model, 
			@PathVariable("idTieuBan") String idTieuBan,
			@PathVariable("idGiangVien") String idGiangVien) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("DELETE FROM HoiDong t WHERE t.tieuBan.idTieuBan = :idTieuBan AND t.giangVien.idGiangVien = :idGiangVien");
			query.setParameter("idTieuBan", idTieuBan);
			query.setParameter("idGiangVien", idGiangVien);
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message", "Hội đồng đã được xóa!");
		return new ModelAndView("forward:/admin/dshoidong/search");
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("hoidong") HoiDongDTO dto, BindingResult result) {
		if(dto.getIdGiangVien() == null) {
			result.rejectValue("idGiangVien", "dto", "Giảng viên không được để trống");
		}
		if(dto.getIdTieuBan() == null) {
			result.rejectValue("idTieuBan", "dto", "Tiểu ban không được để trống");
		}
		if(getHoiDongByIdTieuBanIdGiangVien(dto.getIdTieuBan(), dto.getIdGiangVien()) != null  && dto.getIsEdit() == false) {
			result.rejectValue("idGiangVien", "dto", "Sinh viên đã đăng ký lớp tín chỉ này");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dshoidong/addOrEdit");
		}
		HoiDong entity = new HoiDong();
		BeanUtils.copyProperties(dto, entity);
		
		GiangVien giangVien = new GiangVien();
		giangVien.setIdGiangVien(dto.getIdGiangVien());
		entity.setGiangVien(giangVien);
		
		TieuBan tieuBan = new TieuBan();
		tieuBan.setIdTieuBan(dto.getIdTieuBan());
		entity.setTieuBan(tieuBan);
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			session.saveOrUpdate(entity);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message","Hội đồng đã được lưu!");
		return new ModelAndView("forward:/admin/dshoidong", model);
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query<HoiDong> query = session.createQuery("from HoiDong");
			List<HoiDong> list = query.list();
			model.addAttribute("dshoidong", list);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		return "admin/dshoidong/list";
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenTieuBan", required = false) String tenTieuBan) {
		List<HoiDong> list = null;
		if(StringUtils.hasText(tenTieuBan)) {
			Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
			Transaction t = session.beginTransaction();
			try {
				Query query = session.createQuery("from HoiDong t where t.tenTieuBan like :tenTieuBan");
				query.setParameter("tenTieuBan", "%" + tenTieuBan + "%");
				list = query.list();
				if (list.isEmpty()) {
					model.addAttribute("message","Tiểu ban không tồn tại!");
				}
				t.commit();
			} catch (Exception e) {
				t.rollback();
			} finally {
				session.close();
			}
		}else {
			Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
			Transaction t = session.beginTransaction();
			try {
				Query<HoiDong> query = session.createQuery("from HoiDong");
				list = query.list();
				t.commit();
			} catch (Exception e) {
				t.rollback();
			} finally {
				session.close();
			}
		}
		model.addAttribute("dshoidong", list);
		return new ModelAndView ("admin/dshoidong/search", model);
	}
	@GetMapping("view/{idTieuBan}")
	public String view(ModelMap model, @PathVariable("idTieuBan") String idTieuBan) {
		List<HoiDong> list = null;
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from HoiDong h where h.tieuBan.idTieuBan = :idTieuBan");
			query.setParameter("idTieuBan", idTieuBan);
			list = query.list();
			if (list.isEmpty()) {
				model.addAttribute("message","Danh sách hội đồng trống!");
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("dshoidong", list);
		return "admin/dshoidong/search";
	}
}
