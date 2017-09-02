package com.chinikom;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLoginRestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CounterService counterService;

	@Before("execution(public * com.chinikom.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
		logger.info(":::::AOP Before for User REST call:::::" + pjp);
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.createUserLogin*(..))")
	public void afterCallingCreateUserLogin(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Create User Login REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserLoginController.createUserLogin");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllUserLoginByPage*(..))")
	public void afterCallingGetAllUserLoginByPage(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users getAllUserLoginByPage REST call:::::"
				+ pjp);

		counterService
				.increment("com.chinikom.api.rest.UserLoinController.getAllUserLoinByPage");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllUserLogin*(..))")
	public void afterCallingGetAllUserLogin(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning User getAllUserLogin REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserLoginController.getAllUsersLogin");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.UserLogin*(..))")
	public void afterCallingGetUserLogin(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users getUserLogin REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserLoginController.getUserLogin");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.updateUserLogin*(..))")
	public void afterCallingUpdateUserLogin(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users updateUserLogin REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserLoginController.updateUserLogin");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.deleteUserLogin*(..))")
	public void afterCallingDeleteUserLogin(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users deleteUserLogin REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserLoginController.deleteUserLogin");
	}

	@AfterThrowing(pointcut = "execution(public * com.chinikom.api.rest.*Controller.*(..))", throwing = "e")
	public void afterCustomerThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.UserLogin.controller");
	}
}
