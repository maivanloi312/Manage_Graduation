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

import edu.poly.shop.domain.Quyen;
import edu.poly.shop.domain.TaiKhoan;
import edu.poly.shop.model.QuyenDTO;
import edu.poly.shop.model.TaiKhoanDTO;
import edu.poly.shop.service.QuyenService;
import edu.poly.shop.service.TaiKhoanService;

@Controller
@RequestMapping("admin/dstaikhoan")
public class TaiKhoanController {
	@Autowired
	TaiKhoanService taiKhoanService;
	@Autowired
	QuyenService quyenService;
	
	@ModelAttribute("dsquyen")
	public List<QuyenDTO> getDSQuyen(){
		return quyenService.findAll().stream().map(item->{
			QuyenDTO dto = new QuyenDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}

	@GetMapping("add")
	public String add(Model model) {
		TaiKhoanDTO dto = new TaiKhoanDTO();
		dto.setIsEdit(false);
		model.addAttribute("taikhoan", dto);
		return "admin/dstaikhoan/addOrEdit";
	}
	@GetMapping("edit/{idTaiKhoan}")
	public ModelAndView edit(ModelMap model, @PathVariable("idTaiKhoan") Long idTaiKhoan) {
		Optional<TaiKhoan> opt = taiKhoanService.findById(idTaiKhoan);
		TaiKhoanDTO dto = new TaiKhoanDTO();
		if(opt.isPresent()) {
			TaiKhoan entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIdQuyen(entity.getQuyen().getIdQuyen());
			dto.setIsEdit(true);
			model.addAttribute("taikhoan", dto);
			return new ModelAndView("admin/dstaikhoan/addOrEdit", model);
		}
		model.addAttribute("message", "Tài khoản không tồn tại");
		return new ModelAndView("forward:/admin/dstaikhoan", model);
	}
	@GetMapping("delete/{idTaiKhoan}")
	public ModelAndView delete(ModelMap model, @PathVariable("idTaiKhoan") Long idTaiKhoan) {
		taiKhoanService.deleteById(idTaiKhoan);
		model.addAttribute("message", "Tài khoản đã được xóa!");
		return new ModelAndView("forward:/admin/dstaikhoan/search");
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("taikhoan") TaiKhoanDTO dto, BindingResult result) {
		if(taiKhoanService.findByUsernameIs(dto.getUsername()) != null && dto.getIsEdit() == false) {
			result.rejectValue("username", "dto", "Tên tài khoản đã tồn tại");
		}
		if(!dto.getPassword().isEmpty() && dto.getPassword().length() <3 && dto.getIsEdit() == true) {
			result.rejectValue("password", "dto", "Mật khẩu tối thiểu 3 kí tự");
		}
		if(dto.getPassword().length() < 3 && dto.getIsEdit() == false) {
			result.rejectValue("password", "dto", "Mật khẩu tối thiểu 3 kí tự");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dstaikhoan/addOrEdit");
		}
		TaiKhoan entity = new TaiKhoan();
		BeanUtils.copyProperties(dto, entity);
		
		Quyen quyen = new Quyen();
		quyen.setIdQuyen(dto.getIdQuyen());
		entity.setQuyen(quyen);
		
		taiKhoanService.save(entity);
		model.addAttribute("message","Tài khoản đã được lưu!");
		return new ModelAndView("forward:/admin/dstaikhoan", model);
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<TaiKhoan> list = taiKhoanService.findAll();
		model.addAttribute("dstaikhoan", list);
		return "admin/dstaikhoan/list";
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "username", required = false) String username) {
		List<TaiKhoan> list = null;
		if(StringUtils.hasText(username)) {
			list = taiKhoanService.findByUsernameContaining(username);
			if (list.isEmpty()) {
				model.addAttribute("message","Tài khoản không tồn tại!");
			}
		}else {
			list = taiKhoanService.findAll();
		}
		model.addAttribute("dstaikhoan", list);
		return new ModelAndView ("admin/dstaikhoan/search", model);
	}
}
