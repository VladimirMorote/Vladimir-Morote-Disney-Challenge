package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.dto.CharacterBasicDTO;
import com.example.demo.dto.CharacterDTO;


public interface CharacterService {

	// GET
	List<CharacterBasicDTO> getCharacterBasicList();
	CharacterDTO getCharDetails(Long id);
	
	// POST
	CharacterDTO saveNewCharacter(CharacterDTO newChar);
	
	// DEL
	void deleteCharacterById(Long id);
	
	// PUT
	CharacterDTO editCharacterById(Long id, CharacterDTO charToEdit);
	
	// FILTERS
	List<CharacterDTO> getByFilters(String name, Integer age, Set<Long> movies);
}
