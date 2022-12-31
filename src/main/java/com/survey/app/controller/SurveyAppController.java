package com.survey.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.survey.app.dto.SurveyData;
import com.survey.app.dto.User;
import com.survey.app.repo.SurveyAppRepository;
import com.survey.app.repo.SurveyDataRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SurveyAppController {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SurveyAppRepository repo;

	@Autowired
	SurveyDataRepository surveyRepo;

	@GetMapping(value = "/")
	public void viewHomePage(HttpServletResponse httpServletResponse, HttpSession session) {
		session.setAttribute("user", new User());
		session.setAttribute("signupChange", "true");
		session.setAttribute("loginChange", "true");
		httpServletResponse.setHeader("Location", "/home");
		httpServletResponse.setStatus(302);
	}

	@GetMapping("/home")
	public String viewHomePage(@ModelAttribute String userName, Model model, HttpSession session) {
		model.addAttribute("user", new User());
		return "home";
	}

	@GetMapping("/survey")
	public String viewLoginPage(@ModelAttribute User user, Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {
		model.addAttribute("user", user);
		if (session.getAttribute("loggedin") == null) {
			user.setUsername(null);
			session.setAttribute("user", user);
			httpServletResponse.setHeader("Location", "/");
			httpServletResponse.setStatus(302);
		}

		model.addAttribute("survey", new SurveyData());
		return "survey";
	}

	@PostMapping("/surveyAnswers")
	public String viewSurveyAns(@ModelAttribute User user, @ModelAttribute SurveyData survey, Model model,
			HttpSession session,
			HttpServletResponse httpServletResponse) {
		session.setAttribute("survey", survey);
		model.addAttribute("survey", survey);
		return "surveyAnswers";
	}

	@GetMapping("/surveyAnswers")
	public String retrieveSurveyAns(@ModelAttribute User user, @ModelAttribute SurveyData survey, Model model,
			HttpSession session,
			HttpServletResponse httpServletResponse) {
		SurveyData data = (SurveyData) session.getAttribute("survey");
		model.addAttribute("survey", data);
		return "surveyAnswers";
	}

	@PostMapping("/submit")
	public String submit(@ModelAttribute User user, @ModelAttribute SurveyData survey, Model model,
			HttpSession session,
			HttpServletResponse httpServletResponse) {
		SurveyData data = (SurveyData) session.getAttribute("survey");
		surveyRepo.save(data);
		return "submit";
	}

	@GetMapping("/logout")
	public void logOut(@ModelAttribute User user, Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {
		session.setAttribute("loggedin", null);
		session.setAttribute("survey", null);
		httpServletResponse.setHeader("Location", "/");
		httpServletResponse.setStatus(302);
	}

	@GetMapping("/delete")
	public void delete(@ModelAttribute User user, Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {
		SurveyData data = (SurveyData) session.getAttribute("survey");
		surveyRepo.deleteById(data.getName());
		session.setAttribute("loggedin", null);
		session.setAttribute("survey", null);
		httpServletResponse.setHeader("Location", "/");
		httpServletResponse.setStatus(302);
	}

	@PostMapping("/signup")
	public void signUp(@ModelAttribute User user, Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {
		session.setAttribute("user", user);
		session.setAttribute("loggedin", "true");
		session.setAttribute("loginChange", "false");
		session.setAttribute("signupChange", "true");
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (repo.existsById(user.getUsername())) {
			httpServletResponse.setHeader("Location", "/home");
			httpServletResponse.setStatus(302);
		} else {
			repo.save(user);
			httpServletResponse.setHeader("Location", "/survey");
			httpServletResponse.setStatus(302);
		}

	}

	@PostMapping("/login")
	public RedirectView logIn(@ModelAttribute User user, Model model, HttpSession session,
			HttpServletResponse httpServletResponse) {

		session.setAttribute("user", user);
		session.setAttribute("loggedin", "true");
		session.setAttribute("signupChange", "false");
		session.setAttribute("loginChange", "true");
		if (repo.existsById(user.getUsername())
				&& passwordEncoder.matches(user.getPassword(), repo.findById(user.getUsername()).get().getPassword())) {
			if (surveyRepo.existsById(user.getUsername())) {

				model.addAttribute("survey", surveyRepo.findById(user.getUsername()).get());
				session.setAttribute("survey", surveyRepo.findById(user.getUsername()).get());
				return new RedirectView("/surveyAnswers");
			} else {
				return new RedirectView("/survey");
			}
		} else {
			return new RedirectView("/home");
		}

	}

}
