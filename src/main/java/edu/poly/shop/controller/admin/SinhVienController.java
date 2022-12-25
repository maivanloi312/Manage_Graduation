package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import edu.poly.shop.domain.SinhVien;
import edu.poly.shop.domain.Lop;
import edu.poly.shop.domain.TaiKhoan;
import edu.poly.shop.model.LopDTO;
import edu.poly.shop.model.SinhVienDTO;
import edu.poly.shop.model.TaiKhoanDTO;
import edu.poly.shop.service.LopService;
import edu.poly.shop.service.SinhVienService;
import edu.poly.shop.service.TaiKhoanService;

@Controller
@RequestMapping("admin/dssinhvien")
public class SinhVienController {
	@Autowired
	SinhVienService sinhVienService;
	@Autowired
	LopService lopService;
	@Autowired
	TaiKhoanService taiKhoanService;

	@ModelAttribute("dslop")
	public List<LopDTO> getDSLop(){
		return lopService.findAll().stream().map(item->{
			LopDTO dto = new LopDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	@ModelAttribute("dstaikhoan")
	public List<TaiKhoanDTO> getDSTaiKhoan(){
		return taiKhoanService.getDSTaiKhoanSinhVien().stream().map(item->{
			TaiKhoanDTO dto = new TaiKhoanDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("sinhvien") SinhVienDTO dto, BindingResult result) {
		Optional<SinhVien> opt = sinhVienService.findById(dto.getIdSinhVien());
		if(opt.isPresent() && dto.getIsEdit() == false) {
			result.rejectValue("idSinhVien", "dto", "Mã sinh viên đã tồn tại");
		}
		if(sinhVienService.findByEmailIs(dto.getEmail()) != null && dto.getIsEdit() == false) {
			result.rejectValue("email", "dto", "Email đã tồn tại");
		}
		if (dto.getIdLop() == null) {
			result.rejectValue("idLop", "dto", "Lớp không được để trống");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dssinhvien/addOrEdit");
		}
		SinhVien entity = new SinhVien();
		BeanUtils.copyProperties(dto, entity);
		
		Lop lop = new Lop();
		lop.setIdLop(dto.getIdLop());
		entity.setLop(lop);
		
		TaiKhoan taiKhoan = new TaiKhoan();
	    if (dto.getIdTaiKhoan() == null) {
	    	taiKhoan = null;
	    }
	    else {
			taiKhoan.setIdTaiKhoan(dto.getIdTaiKhoan());
		}
		entity.setTaiKhoanSinhVien(taiKhoan);
		
		sinhVienService.save(entity);
		model.addAttribute("message","Sinh viên đã được lưu!");
		return new ModelAndView("forward:/admin/dssinhvien", model);
	}
	@GetMapping("add")
	public String add(Model model) {
		SinhVienDTO dto = new SinhVienDTO();
		dto.setIsEdit(false);
		model.addAttribute("sinhvien", dto);
		return "admin/dssinhvien/addOrEdit";
	}
	@GetMapping("edit/{idSinhVien}")
	public ModelAndView edit(ModelMap model, @PathVariable("idSinhVien") String idSinhVien) {
		Optional<SinhVien> opt = sinhVienService.findById(idSinhVien);
		SinhVienDTO dto = new SinhVienDTO();
		if(opt.isPresent()) {
			SinhVien entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIdLop(entity.getLop().getIdLop());
			if(entity.getTaiKhoanSinhVien() != null) {
				dto.setIdTaiKhoan(entity.getTaiKhoanSinhVien().getIdTaiKhoan());
			}
			dto.setIsEdit(true);
			model.addAttribute("sinhvien", dto);
			return new ModelAndView("admin/dssinhvien/addOrEdit", model);
		}
		model.addAttribute("message", "Sinh viên không tồn tại");
		return new ModelAndView("forward:/admin/dssinhvien", model);
	}
	@GetMapping("delete/{idSinhVien}")
	public ModelAndView delete(ModelMap model, @PathVariable("idSinhVien") String idSinhVien) {
		sinhVienService.deleteById(idSinhVien);
		model.addAttribute("message", "Sinh viên đã được xóa!");
		return new ModelAndView("forward:/admin/dssinhvien/search");
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<SinhVien> list = sinhVienService.findAll();
		model.addAttribute("dssinhvien", list);
		return "admin/dssinhvien/list";
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenSinhVien", required = false) String tenSinhVien) {
		List<SinhVien> list = null;
		if(StringUtils.hasText(tenSinhVien)) {
			list = sinhVienService.findByTenSinhVienContaining(tenSinhVien);
			if (list.isEmpty()) {
				model.addAttribute("message","Sinh viên không tồn tại!");
			}
		}else {
			list = sinhVienService.findAll();
		}
		model.addAttribute("dssinhvien", list);
		return new ModelAndView ("admin/dssinhvien/search", model);
	}
	@GetMapping("check")
	public ModelAndView check(ModelMap model,
			@RequestParam(name = "GPA", required = false) Float GPA) {
		List<SinhVien> list = null;
		if(StringUtils.hasText(GPA.toString())) {
			list = sinhVienService.findByGPAGreaterThanEqual(GPA);
			if (list.isEmpty()) {
				model.addAttribute("message","Không có sinh viên nào đủ điều kiện thực hiện đồ án");
			}
		}else {
			list = sinhVienService.findAll();
		}
		model.addAttribute("dssinhvien", list);
		return new ModelAndView ("admin/dssinhvien/search", model);
	}
	@GetMapping("view/{idLop}")
	public String view(ModelMap model, @PathVariable("idLop") String idLop) {
		List<SinhVien> list =  sinhVienService.findAllByLopIdLop(idLop);
		if (list.isEmpty()) {
			model.addAttribute("message","Danh sách sinh viên trống!");
		}
		model.addAttribute("dssinhvien", list);
		return "admin/dssinhvien/search";
	}
}
