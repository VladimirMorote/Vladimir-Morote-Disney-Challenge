package com.example.demo.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterFiltersDTO {

	private String name;
	private Integer age;
	private Set<Long> movies;
	
}
