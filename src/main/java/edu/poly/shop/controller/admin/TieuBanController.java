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

import edu.poly.shop.domain.TieuBan;
import edu.poly.shop.model.TieuBanDTO;
import edu.poly.shop.service.TieuBanService;

@Controller
@RequestMapping("admin/dstieuban")
public class TieuBanController {
	@Autowired
	TieuBanService tieuBanService;
	@GetMapping("add")
	public String add(Model model) {
		TieuBanDTO dto = new TieuBanDTO();
		dto.setIsEdit(false);
		model.addAttribute("tieuban", dto);
		return "admin/dstieuban/addOrEdit";
	}
	@GetMapping("edit/{idTieuBan}")
	public ModelAndView edit(ModelMap model, @PathVariable("idTieuBan") String idTieuBan) {
		Optional<TieuBan> opt = tieuBanService.findById(idTieuBan);
		TieuBanDTO dto = new TieuBanDTO();
		if(opt.isPresent()) {
			TieuBan entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("tieuban", dto);
			return new ModelAndView("admin/dstieuban/addOrEdit", model);
		}
		model.addAttribute("message", "Tiểu ban không tồn tại");
		return new ModelAndView("forward:/admin/dstieuban", model);
	}
	@GetMapping("delete/{idTieuBan}")
	public ModelAndView delete(ModelMap model, @PathVariable("idTieuBan") String idTieuBan) {
		tieuBanService.deleteById(idTieuBan);
		model.addAttribute("message", "Tiểu ban đã được xóa!");
		return new ModelAndView("forward:/admin/dstieuban/search");
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("tieuban") TieuBanDTO dto, BindingResult result) {
		Optional<TieuBan> opt = tieuBanService.findById(dto.getIdTieuBan());
		if(opt.isPresent() && dto.getIsEdit() == false) {
			result.rejectValue("idTieuBan", "dto", "Mã tiểu ban đã tồn tại");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dstieuban/addOrEdit");
		}
		TieuBan entity = new TieuBan();
		BeanUtils.copyProperties(dto, entity);
		tieuBanService.save(entity);
		model.addAttribute("message","Tiểu ban đã được lưu!");
		return new ModelAndView("forward:/admin/dstieuban", model);
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<TieuBan> list = tieuBanService.findAll();
		model.addAttribute("dstieuban", list);
		return "admin/dstieuban/list";
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenTieuBan", required = false) String tenTieuBan) {
		List<TieuBan> list = null;
		if(StringUtils.hasText(tenTieuBan)) {
			list = tieuBanService.findByTenTieuBanContaining(tenTieuBan);
			if (list.isEmpty()) {
				model.addAttribute("message","Tiểu ban không tồn tại!");
			}
		}else {
			list = tieuBanService.findAll();
		}
		model.addAttribute("dstieuban", list);
		return new ModelAndView ("admin/dstieuban/search", model);
	}
}
