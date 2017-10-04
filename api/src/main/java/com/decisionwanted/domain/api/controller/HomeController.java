package com.decisionwanted.domain.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody String showHomePage() {
		return "DecisionWanted API v1.0";
	}

}
