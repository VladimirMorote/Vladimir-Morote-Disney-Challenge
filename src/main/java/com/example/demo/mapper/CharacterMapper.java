package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.CharacterBasicDTO;
import com.example.demo.dto.CharacterDTO;
import com.example.demo.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CharacterMapper {

	// Mappers:
	@Autowired
	private MovieMapper movieMapper;

	// === DTO -> Entity ===
	public CharacterEntity charDTO2Entity(CharacterDTO newChar) {
		CharacterEntity newEntity = new CharacterEntity();
		newEntity.setImage(newChar.getImage());
		newEntity.setName(newChar.getName());
		newEntity.setAge(newChar.getAge());
		newEntity.setWeight(newChar.getWeight());
		newEntity.setHistory(newChar.getHistory());


		return newEntity;
	}

	// --- Entity -> DTO ---
	public CharacterDTO entity2DTO(CharacterEntity savedEntity, Boolean fetchMovies) {
		CharacterDTO newDTO = new CharacterDTO();
		newDTO.setId(savedEntity.getId());
		newDTO.setImage(savedEntity.getImage());
		newDTO.setName(savedEntity.getName());
		newDTO.setAge(savedEntity.getAge());
		newDTO.setWeight(savedEntity.getWeight());
		newDTO.setHistory(savedEntity.getHistory());
		if (fetchMovies) {
			newDTO.setCharacterMovies(movieMapper.movieEntityList2DTOList(savedEntity.getCharacterMovies(), false));
		}
		return newDTO;
	}

	// --- List<Entity> -> List<DTO> ---
	public List<CharacterDTO> charListEntity2DTOList(List<CharacterEntity> movieCharacters, boolean b) {
		List<CharacterDTO> newList = new ArrayList<>();
		for (CharacterEntity ent : movieCharacters) {
			newList.add(this.entity2DTO(ent, b));
		}
		return newList;
	}

	// --- Entity -> BasicDTO ---
	private CharacterBasicDTO charEntity2BasicDTO(CharacterEntity ch) {
		CharacterBasicDTO dto = new CharacterBasicDTO();
		dto.setImage(ch.getImage());
		dto.setName(ch.getName());
		return dto;
	}

	// --- BasicList<Entity> -> BasicList<DTO> ---
	public List<CharacterBasicDTO> basicListEntity2DTO(List<CharacterEntity> myList) {
		List<CharacterBasicDTO> listDTO = new ArrayList<>();
		for (CharacterEntity ch : myList) {
			listDTO.add(this.charEntity2BasicDTO(ch));
		}
		return listDTO;
	}
}