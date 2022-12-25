package edu.poly.shop.controller;

import javax.persistence.EntityManagerFactory;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Quyen;
import edu.poly.shop.domain.TaiKhoan;
import edu.poly.shop.model.RegistrationAccountDTO;
import edu.poly.shop.service.TaiKhoanService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	@Autowired
	TaiKhoanService taiKhoanService;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@GetMapping
	public String showRegistrationPage(Model model) {
		RegistrationAccountDTO dto = new RegistrationAccountDTO();
		model.addAttribute("taikhoan", dto);
		return "registration";
	}
	@PostMapping
	public ModelAndView registerUserAccount(ModelMap model,
			@Valid @ModelAttribute("taikhoan") RegistrationAccountDTO dto, BindingResult result) {
		if(taiKhoanService.findByUsernameIs(dto.getUsername()) != null) {
			result.rejectValue("username", "dto", "Tên tài khoản đã được sử dụng");
		}
		if(result.hasErrors()) {
			return new ModelAndView("registration");
		}
		TaiKhoan entity = new TaiKhoan();
		BeanUtils.copyProperties(dto, entity);
		Quyen quyen = new Quyen();
		quyen.setIdQuyen(new Long(2));
		entity.setQuyen(quyen);
		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction t = session.beginTransaction();
		try {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			session.save(entity);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		} finally {
			session.close();
		}
		return new ModelAndView("redirect:/registration?success", model);
	}
}
