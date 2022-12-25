package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;

//import javax.persistence.EntityManagerFactory;
import javax.validation.Valid;

//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
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

import edu.poly.shop.domain.Lop;
import edu.poly.shop.model.LopDTO;
import edu.poly.shop.service.LopService;

@Controller
@RequestMapping("admin/dslop")
public class LopController {	
	@Autowired
	LopService lopService;
	
	@GetMapping("add")
	public String add(Model model) {
		LopDTO dto= new LopDTO();
		dto.setIsEdit(false);
		model.addAttribute("lop", dto);
		return "admin/dslop/addOrEdit";
	}
	@GetMapping("edit/{idLop}")
	public ModelAndView edit(ModelMap model, @PathVariable("idLop") String idLop) {
		Optional<Lop> opt = lopService.findById(idLop);
		LopDTO dto = new LopDTO();
		if(opt.isPresent()) {
			Lop entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("lop", dto);
			return new ModelAndView("admin/dslop/addOrEdit", model);
		}
		model.addAttribute("message", "Lớp không tồn tại");
		return new ModelAndView("forward:/admin/dslop", model);
	}
	@GetMapping("delete/{idLop}")
	public ModelAndView delete(ModelMap model, @PathVariable("idLop") String idLop) {
		lopService.deleteById(idLop);
		model.addAttribute("message", "Lớp đã được xóa!");
		return new ModelAndView("forward:/admin/dslop/search");
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("lop") LopDTO dto, BindingResult result) {
		Optional<Lop> opt = lopService.findById(dto.getIdLop());
		if(opt.isPresent() && dto.getIsEdit() == false) {
			result.rejectValue("idLop", "dto", "Mã lớp đã tồn tại");
		}
		if(result.hasErrors()) {
			return new ModelAndView("admin/dslop/addOrEdit");
		}
		Lop entity = new Lop();
		BeanUtils.copyProperties(dto, entity);
		
		lopService.save(entity);
		model.addAttribute("message","Lớp đã được lưu!");
		return new ModelAndView("forward:/admin/dslop", model);
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Lop> list = lopService.findAll();
		model.addAttribute("dslop", list);
		return "admin/dslop/list";
	}
	@GetMapping("search")
	public ModelAndView search(ModelMap model,
			@RequestParam(name = "tenLop", required = false) String tenLop) {
		List<Lop> list = null;
		if(StringUtils.hasText(tenLop)) {
			list = lopService.findByTenLopContaining(tenLop);
			if (list.isEmpty()) {
				model.addAttribute("message","Lớp không tồn tại!");
			}
		}else {
			list = lopService.findAll();
		}
		model.addAttribute("dslop", list);
		return new ModelAndView ("admin/dslop/search", model);
	}
}
