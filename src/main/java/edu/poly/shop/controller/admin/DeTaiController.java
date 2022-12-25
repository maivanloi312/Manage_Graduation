package edu.poly.shop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.DeTai;
import edu.poly.shop.domain.GiangVien;
import edu.poly.shop.domain.SinhVien;
import edu.poly.shop.domain.TieuBan;
import edu.poly.shop.model.DeTaiDTO;
import edu.poly.shop.model.GiangVienDTO;
import edu.poly.shop.model.SinhVienDTO;
import edu.poly.shop.model.TieuBanDTO;
import edu.poly.shop.service.GiangVienService;
import edu.poly.shop.service.SinhVienService;
import edu.poly.shop.service.StorageService;
import edu.poly.shop.service.TieuBanService;

@Controller
@RequestMapping("admin/dsdetai")
public class DeTaiController {
	private static final Logger logger = LoggerFactory.getLogger(DeTaiController.class);
	@Autowired
	StorageService storageService;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	@Autowired
	GiangVienService giangVienService;
	@Autowired
	TieuBanService tieuBanService;
	@Autowired
	SinhVienService sinhVienService;

	public DeTai getDeTaiByTenDeTai(String tenDeTai) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from DeTai t where t.tenDeTai = :tenDeTai");
			query.setParameter("tenDeTai", tenDeTai);
			DeTai entity = (DeTai) query.list().get(0);
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
	
	@ModelAttribute("dssinhvien")
	public List<SinhVienDTO> getDSSinhVien(){
		return sinhVienService.getDSSinhVien().stream().map(item->{
			SinhVienDTO dto = new SinhVienDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
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
		DeTaiDTO dto = new DeTaiDTO();
		dto.setIsEdit(false);
		model.addAttribute("detai", dto);
		return "admin/dsdetai/addOrEdit";
	}
	
	@GetMapping("/documents/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = storageService.loadAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
	@GetMapping("edit/{idSinhVien}")
	public ModelAndView edit(ModelMap model, @PathVariable("idSinhVien") String idSinhVien) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from DeTai d where d.sinhVien.idSinhVien = :idSinhVien");
			query.setParameter("idSinhVien", idSinhVien);
			DeTai entity = (DeTai) query.list().get(0);
			DeTaiDTO dto = new DeTaiDTO();
			if(entity != null) {
				BeanUtils.copyProperties(entity, dto);
				dto.setIsEdit(true);
				dto.setIdSinhVien(entity.getSinhVien().getIdSinhVien());
				if (entity.getGiangVienHuongDan() != null) {
					dto.setIdGiangVienHuongDan(entity.getGiangVienHuongDan().getIdGiangVien());
				}
				if (entity.getGiangVienPhanBien() != null) {
					dto.setIdGiangVienPhanBien(entity.getGiangVienPhanBien().getIdGiangVien());
				}
				if (entity.getTieuBanDeTai() != null) {
					dto.setIdTieuBan(entity.getTieuBanDeTai().getIdTieuBan());
				}
				model.addAttribute("detai", dto);
				return new ModelAndView("admin/dsdetai/addOrEdit", model);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message", "Đề tài không tồn tại");
		return new ModelAndView("forward:/admin/dsdetai", model);
	}
	
	@GetMapping("delete/{idSinhVien}")
	public ModelAndView delete(ModelMap model, @PathVariable("idSinhVien") String idSinhVien) throws IOException {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("DELETE FROM DeTai d where d.sinhVien.idSinhVien = :idSinhVien");
			query.setParameter("idSinhVien", idSinhVien);
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("message", "Đề tài đã được xóa!");
		return new ModelAndView("forward:/admin/dsdetai/search");
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("detai") DeTaiDTO dto, BindingResult result) {
		if(dto.getIdSinhVien() == null) {
			result.rejectValue("idSinhVien", "dto", "Sinh viên không được để trống");
		}
		if(getDeTaiByTenDeTai(dto.getTenDeTai()) != null && dto.getIsEdit() == false) {
			result.rejectValue("tenDeTai", "dto", "Tên đề tài đã tồn tại");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dsdetai/addOrEdit");
		}
		DeTai entity = new DeTai();
		BeanUtils.copyProperties(dto, entity);
		
		if (dto.getDiemHuongDan() != null && dto.getDiemPhanBien() != null && dto.getDiemTieuBan() != null) {
			Float diemTongFloat = (dto.getDiemHuongDan() + dto.getDiemPhanBien() + dto.getDiemTieuBan()) / 3;
			entity.setDiemTong((float)(Math.round(diemTongFloat * 10) / 10));		
		}
		GiangVien giangVienHuongDan = new GiangVien();
	    if (dto.getIdGiangVienHuongDan().isEmpty()) {
	    	giangVienHuongDan = null;
	    }
	    else {
	    	giangVienHuongDan.setIdGiangVien(dto.getIdGiangVienHuongDan());
		}
		entity.setGiangVienHuongDan(giangVienHuongDan);
		
		GiangVien giangVienPhanBien = new GiangVien();
	    if (dto.getIdGiangVienPhanBien().isEmpty()) {
	    	giangVienPhanBien = null;
	    }
	    else {
			giangVienPhanBien.setIdGiangVien(dto.getIdGiangVienPhanBien());
		}
		entity.setGiangVienPhanBien(giangVienPhanBien);
		
		TieuBan tieuBan = new TieuBan();
	    if (dto.getIdTieuBan().isEmpty()) {
	    	tieuBan = null;
	    }
	    else {
			tieuBan.setIdTieuBan(dto.getIdTieuBan());
		}
		entity.setTieuBanDeTai(tieuBan);
		
		SinhVien sinhVien = new SinhVien();
		sinhVien.setIdSinhVien(dto.getIdSinhVien());
		entity.setSinhVien(sinhVien);
		
		if(!dto.getBanMemDoAnFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			entity.setBanMemDoAn(storageService.getStorageFilename(dto.getBanMemDoAnFile(), uuString));
			storageService.store(dto.getBanMemDoAnFile(), entity.getBanMemDoAn());
		}
		
		if(!dto.getSourceCodeFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			entity.setSourceCode(storageService.getStorageFilename(dto.getSourceCodeFile(), uuString));
			storageService.store(dto.getSourceCodeFile(), entity.getSourceCode());
		}
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
		model.addAttribute("message","Đề tài đã được lưu!");
		return new ModelAndView("forward:/admin/dsdetai", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query<DeTai> query = session.createQuery("from DeTai");
			List<DeTai> list = query.list();
			model.addAttribute("dsdetai", list);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		return "admin/dsdetai/list";
	}
	
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenDeTai", required = false) String tenDeTai) {
		List<DeTai> list = null;
		if(StringUtils.hasText(tenDeTai)) {
			Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
			Transaction t = session.beginTransaction();
			try {
				Query query = session.createQuery("from DeTai d where d.tenDeTai like :tenDeTai");
				query.setParameter("tenDeTai", "%" + tenDeTai + "%");
				list = query.list();
				if (list.isEmpty()) {
					model.addAttribute("message","Đề tài không tồn tại!");
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
				Query<DeTai> query = session.createQuery("from DeTai");
				list = query.list();
				t.commit();
			} catch (Exception e) {
				t.rollback();
			} finally {
				session.close();
			}
		}
		model.addAttribute("dsdetai", list);
		return new ModelAndView("admin/dsdetai/search", model);
	}	
	@GetMapping("view/{idSinhVien}")
	public String view(ModelMap model, @PathVariable("idSinhVien") String idSinhVien) {
		List<DeTai> list = null;
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			Query query = session.createQuery("from DeTai d where d.sinhVien.idSinhVien = :idSinhVien");
			query.setParameter("idSinhVien", idSinhVien);
			list = query.list();
			if (list.isEmpty()) {
				model.addAttribute("message","Danh sách đề tài trống!");
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		model.addAttribute("dsdetai", list);
		return "admin/dsdetai/search";
	}
}
