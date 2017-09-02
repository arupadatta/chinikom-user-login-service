package com.chinikom.service;

import org.springframework.context.ApplicationEvent;

import com.chinikom.domain.UserLogin;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */
public class ServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserLogin eventUserLogin;
	String eventType;

	public ServiceEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return "My eventUserLoginService Event";
	}

	public UserLogin getEventUser() {
		return eventUserLogin;
	}

	public void setEventUserLogin(UserLogin eventUserLogin) {
		this.eventUserLogin = eventUserLogin;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public ServiceEvent(Object source, UserLogin eventUserLogin,
			String eventType) {
		super(source);
		this.eventUserLogin = eventUserLogin;
		this.eventType = eventType;
	}

}
