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

import edu.poly.shop.domain.GiangVien;
import edu.poly.shop.domain.TaiKhoan;
import edu.poly.shop.model.GiangVienDTO;
import edu.poly.shop.model.TaiKhoanDTO;
import edu.poly.shop.service.GiangVienService;
import edu.poly.shop.service.TaiKhoanService;

@Controller
@RequestMapping("admin/dsgiangvien")
public class GiangVienController {
	@Autowired
	GiangVienService giangVienService;
	@Autowired
	TaiKhoanService taiKhoanService;

	@ModelAttribute("dstaikhoan")
	public List<TaiKhoanDTO> getDSTaiKhoan(){
		return taiKhoanService.getDSTaiKhoanGiangVien().stream().map(item->{
			TaiKhoanDTO dto = new TaiKhoanDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("giangvien") GiangVienDTO dto, BindingResult result) {
		Optional<GiangVien> opt = giangVienService.findById(dto.getIdGiangVien());
		if(opt.isPresent() && dto.getIsEdit() == false) {
			result.rejectValue("idGiangVien", "dto", "Mã giảng viên đã tồn tại");
		}
		if(giangVienService.findByEmailIs(dto.getEmail()) != null && dto.getIsEdit() == false) {
			result.rejectValue("email", "dto", "Email đã tồn tại");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dsgiangvien/addOrEdit");
		}
		GiangVien entity = new GiangVien();
		BeanUtils.copyProperties(dto, entity);
		
		TaiKhoan taiKhoan = new TaiKhoan();
	    if (dto.getIdTaiKhoan() == null) {
	    	taiKhoan = null;
	    }
	    else {
			taiKhoan.setIdTaiKhoan(dto.getIdTaiKhoan());
		}
    	entity.setTaiKhoanGiangVien(taiKhoan);
    	giangVienService.save(entity);
		model.addAttribute("message","Giảng viên đã được lưu!");
		return new ModelAndView("forward:/admin/dsgiangvien", model);
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<GiangVien> list = giangVienService.findAll();
		model.addAttribute("dsgiangvien", list);
		return "admin/dsgiangvien/list";
	}
	@GetMapping("add")
	public String add(Model model) {
		GiangVienDTO dto = new GiangVienDTO();
		dto.setIsEdit(false);
		model.addAttribute("giangvien", dto);
		return "admin/dsgiangvien/addOrEdit";
	}
	@GetMapping("edit/{idGiangVien}")
	public ModelAndView edit(ModelMap model, @PathVariable("idGiangVien") String idGiangVien) {
		Optional<GiangVien> opt = giangVienService.findById(idGiangVien);
		GiangVienDTO dto = new GiangVienDTO();
		if(opt.isPresent()) {
			GiangVien entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			if (entity.getTaiKhoanGiangVien() != null) {
				dto.setIdTaiKhoan(entity.getTaiKhoanGiangVien().getIdTaiKhoan());
			}
			dto.setIsEdit(true);
			model.addAttribute("giangvien", dto);
			return new ModelAndView("admin/dsgiangvien/addOrEdit", model);
		}
		model.addAttribute("message", "Giảng viên không tồn tại");
		return new ModelAndView("forward:/admin/dsgiangvien", model);
	}
	
	@GetMapping("delete/{idGiangVien}")
	public ModelAndView delete(ModelMap model, @PathVariable("idGiangVien") String idGiangVien) {
		giangVienService.deleteById(idGiangVien);
		model.addAttribute("message", "Giảng viên đã được xóa!");
		return new ModelAndView("forward:/admin/dsgiangvien/search");
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenGiangVien", required = false) String tenGiangVien) {
		List<GiangVien> list = null;
		if(StringUtils.hasText(tenGiangVien)) {
			list = giangVienService.findByTenGiangVienContaining(tenGiangVien);
			if (list.isEmpty()) {
				model.addAttribute("message","Giảng viên không tồn tại!");
			}
		}else {
			list = giangVienService.findAll();
		}
		model.addAttribute("dsgiangvien", list);
		return new ModelAndView("admin/dsgiangvien/search", model);
	}
}
