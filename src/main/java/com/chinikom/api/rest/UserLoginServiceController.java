package com.chinikom.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinikom.domain.UserLogin;
import com.chinikom.exception.HTTP400Exception;
import com.chinikom.service.ChinikomUserLoginService;
import com.chinikom.service.ServiceEvent;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */
@RestController
@RequestMapping(value = "/chinikom-user-login-service/v1/userLogin")
public class UserLoginServiceController extends AbstractRestController {

	@Autowired
	private ChinikomUserLoginService userLoginService;

	@Autowired
	CounterService counterService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createUserLogin(@RequestBody UserLogin login,
			HttpServletRequest request, HttpServletResponse response) {
		UserLogin createdUserLogin = this.userLoginService
				.createUserLogin(login);
		if (createdUserLogin != null) {
			counterService.increment("com.chinikom.userlogin.created.success");
			eventPublisher.publishEvent(new ServiceEvent(this,
					createdUserLogin, "CustomerCreated"));
		} else {
			counterService.increment("com.chinikom.userlogin.created.failure");
		}
		response.setHeader("Location", request.getRequestURL().append("/")
				.append(createdUserLogin.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Page<UserLogin> getAllUserLoginByPage(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.userLoginService.getAllUserLoginByPage(page, size);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<UserLogin> getAllUserLogin(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.userLoginService.getAllUsersLogin();
	}

	// @RequestMapping("/simple/{emailId}")
	// public UserLogin getSimpleUserLoginByEmail(
	// @PathVariable("emailId") String emailId) {
	// UserLogin userLogin = this.userLoginService
	// .getUserLoginByEmail(emailId);
	// checkResourceFound(userLogin);
	// return userLogin;
	// }
	//

	@RequestMapping(value = "/byemail/{emailId}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	UserLogin getUserLoginByEmail(@PathVariable("emailId") String emailId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserLogin userLogin = this.userLoginService
				.getUserLoginByEmail(emailId);
		checkResourceFound(userLogin);
		return userLogin;
	}

	@RequestMapping("/simple/{id}")
	public UserLogin getSimpleUserLoginById(@PathVariable("id") Long id) {
		UserLogin userLogin = this.userLoginService.getUserLoginById(id);
		checkResourceFound(userLogin);
		return userLogin;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	UserLogin getUserLoginById(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserLogin userLogin = this.userLoginService.getUserLoginById(id);
		checkResourceFound(userLogin);
		return userLogin;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable("id") Long id,
			@RequestBody UserLogin userLogin, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.userLoginService.getUserLoginById(id));
		if (id != userLogin.getId())
			throw new HTTP400Exception("ID doesn't match!");
		counterService.increment("com.chinikom.userlogin.updated.success");

		this.userLoginService.updateUserLogin(userLogin);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUserLogin(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.userLoginService.getUserLoginById(id));
		counterService.increment("com.chinikom.userlogin.deleted.success");
		this.userLoginService.deleteUserLogin(id);
	}
}
