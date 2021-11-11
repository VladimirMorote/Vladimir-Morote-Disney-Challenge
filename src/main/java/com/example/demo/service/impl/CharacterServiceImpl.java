package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.dto.CharacterBasicDTO;
import com.example.demo.dto.CharacterDTO;
import com.example.demo.dto.CharacterFiltersDTO;
import com.example.demo.entity.CharacterEntity;

import com.example.demo.exception.ParamNotFound;
import com.example.demo.mapper.CharacterMapper;
import com.example.demo.repository.CharacterRepository;

import com.example.demo.repository.specifications.CharacterSpecification;
import com.example.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CharacterServiceImpl implements CharacterService {
	
	// Repository:
	@Autowired
	private CharacterRepository charRepo;
	
	// Mapper:
	@Autowired
	private CharacterMapper charMapper;
	
	// Specifications:
	@Autowired
	private CharacterSpecification charSpecs;
	

	// --- GET ---
	@Override
	public List<CharacterBasicDTO> getCharacterBasicList() {	
		List<CharacterEntity> myList = charRepo.findAll();	
		List<CharacterBasicDTO> resultDTO = charMapper.basicListEntity2DTO(myList);
		return resultDTO;
	}
	
	@Override
	public CharacterDTO getCharDetails(Long id) {		
		CharacterEntity dbChar = this.handleFindById(id);		
		CharacterDTO resultDTO = charMapper.entity2DTO(dbChar,true);
		return resultDTO;		
	}

	// --- POST ---
	@Override
	public CharacterDTO saveNewCharacter(CharacterDTO newChar) {
		CharacterEntity newEntity = charMapper.charDTO2Entity(newChar);
		CharacterEntity savedEntity = charRepo.save(newEntity);
		CharacterDTO savedChar = charMapper.entity2DTO(savedEntity, false);			
		return savedChar;
	}
	
	// --- DELETE ---
	@Override
	public void deleteCharacterById(Long id) {
		charRepo.deleteById(id);		
	}
	
	// == PUT ==
	@Override
	public CharacterDTO editCharacterById(Long id, CharacterDTO charToEdit) {		
		CharacterEntity savedChar = this.handleFindById(id);		
		savedChar.setImage(charToEdit.getImage());
		savedChar.setName(charToEdit.getName());
		savedChar.setAge(charToEdit.getAge());
		savedChar.setWeight(charToEdit.getWeight());
		savedChar.setHistory(charToEdit.getHistory());		
		CharacterEntity editedChar = charRepo.save(savedChar);
		CharacterDTO resultDTO = charMapper.entity2DTO(editedChar, false);			
		return resultDTO;
	}

	// --- FILTERS ---
	@Override
	public List<CharacterDTO> getByFilters(String name, Integer age, Set<Long> movies) {
		CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, movies);
		List<CharacterEntity> entityList = charRepo.findAll(charSpecs.getFiltered(filtersDTO));
		List<CharacterDTO> resultDTO = charMapper.charListEntity2DTOList(entityList, true);
		return resultDTO;
	}	
	
	// --- ERROR HANDLING ---
	public CharacterEntity handleFindById(Long id) {
		Optional<CharacterEntity> toBeFound = charRepo.findById(id);
		if(!toBeFound.isPresent()) {
			throw new ParamNotFound("No Character for id: " + id);
		}
		return toBeFound.get();
	}
	
}
