package com.app.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class AuthController {
	@GetMapping("/get")
	@PreAuthorize("hasAuthority('READ')")
	public String helloGet()
	{
		return "Hola mundo - GET";
	}
	
	@PostMapping("/post")
	@PreAuthorize("hasAuthority('CREATE') or hasAuthority('READ')")
	public String helloPost()
	{
		return "Hola mundo - POST";
	}
	
	@PutMapping("/put")
	//@PreAuthorize("")
	public String helloPut()
	{
		return "Hola mundo - PUT";
	}
	
	@DeleteMapping("/delete")
	//@PreAuthorize("")
	public String helloDelete()
	{
		return "Hola mundo - DELETE";
	}
	
	@PatchMapping("/patch")
	@PreAuthorize("hasRole('DEVELOPER')")
	public String helloPatch()
	{
		return "Hola mundo - PATCH";
	}
	
}
