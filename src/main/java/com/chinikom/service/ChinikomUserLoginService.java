package com.chinikom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chinikom.dao.jpa.ChinikomUserLoginDetailsRepository;
import com.chinikom.domain.UserLogin;

/*
 * Service class to do CRUD for User and Address through JPS Repository
 */
@Service
public class ChinikomUserLoginService {

	private static final Logger log = LoggerFactory
			.getLogger(ChinikomUserLoginService.class);

	@Autowired
	private ChinikomUserLoginDetailsRepository userLoginRepository;

	@Autowired
	CounterService counterService;

	@Autowired
	GaugeService gaugeService;

	public ChinikomUserLoginService() {
	}

	public UserLogin createUserLogin(UserLogin userL) {
		if (userL.getEmailId() != null) {
			log.info("User login id/emailid :" + userL.getEmailId());
		} else {
			log.info("User's emailid is null :");
		}

		return userLoginRepository.save(userL);
	}

	public UserLogin getUserLoginById(long id) {
		return userLoginRepository.findUserById(id);
	}

	public UserLogin getUserLoginByEmail(String emailid) {
		return userLoginRepository.findUserByEmailId(emailid);
	}

	public void updateUserLogin(UserLogin user) {
		userLoginRepository.save(user);
	}

	public void deleteUserLogin(Long id) {
		userLoginRepository.delete(id);
	}

	public Page<UserLogin> getAllUserLoginByPage(Integer page, Integer size) {
		Page<UserLogin> pageOfUsers = userLoginRepository
				.findAll(new PageRequest(page, size));
		// example of adding to the /metrics
		if (size > 50) {
			counterService.increment("com.chinikom.getAll.largePayload");
		}
		return pageOfUsers;
	}

	public List<UserLogin> getAllUsersLogin() {
		Iterable<UserLogin> pageOfUsersLogin = userLoginRepository.findAll();
		List<UserLogin> usersLogin = new ArrayList<UserLogin>();
		for (UserLogin userLogin : pageOfUsersLogin) {
			usersLogin.add(userLogin);
		}
		log.info("In Real Service getAllAddresses size :" + usersLogin.size());

		return usersLogin;
	}
}
