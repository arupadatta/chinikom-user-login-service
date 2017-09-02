package com.chinikom.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinikom.domain.UserLogin;

public interface ChinikomUserLoginDetailsRepository extends
		PagingAndSortingRepository<UserLogin, Long> {

	UserLogin findUserByEmailId(String emailId);

	UserLogin findUserById(long id);

	@Override
	Page<UserLogin> findAll(Pageable pageable);

}
