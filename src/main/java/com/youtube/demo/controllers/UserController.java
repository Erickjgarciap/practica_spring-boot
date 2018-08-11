package com.youtube.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.demo.model.User;
import com.youtube.demo.service.UserService;

import demo.youtube.demo.util.RestResponse;

@RestController
public class UserController {
	
	@Autowired
	protected UserService userService;
	
	protected ObjectMapper mapper;
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String userJson ) /*@RequestParam Map<String, String> body*/
			throws JsonParseException, JsonMappingException, IOException {
		this.mapper = new ObjectMapper();
		
		User user = this.mapper.readValue(userJson, User.class);

		if (!this.validate(user)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Los campos obligatorios no estan ");
		}
		this.userService.save(user);

		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
	} 
	
	private boolean validate(User user){
		boolean isValid=true;
		
		if( user.getFirstname() == null ) {
			isValid=false;
		}
		if( user.getFirstsurname() == null) {
			isValid=false;
		}
		if( user.getAdrres() == null) {
			isValid=false;
		}
		return isValid;
	} 
}
