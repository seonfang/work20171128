package com.example.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "foo")
public class FooProperties {
	private String username;
	private String password;
 	private List foolist;
	private List<String> employs = new ArrayList<String>();
	
}
