package co.com.ingeneo.logistica.security;

import lombok.Data;

@Data
public class AuthResponse {
	
	private Integer id;
	private String user;
	private String access_token;

}
