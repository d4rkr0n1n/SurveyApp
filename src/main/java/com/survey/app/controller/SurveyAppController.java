package com.survey.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SurveyAppController {
	 @RequestMapping(value = "/")
	public String viewHomePage() {

		return "home";
	}
	 @RequestMapping(value = "/login")
	 public String viewLoginPage() {
		 viewHelloPage();
		 return "login";
	 }
	 @RequestMapping(value = "/home")
	 public String viewPage() {
		 
		 return "home";
	 }
	 @RequestMapping(value = "/hello")
	 public String viewHelloPage() {
		 
		 
		 return "hello";
	 }

}
