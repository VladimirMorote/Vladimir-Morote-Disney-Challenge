package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import com.example.demo.dto.CharacterBasicDTO;
import com.example.demo.dto.CharacterDTO;
import com.example.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("characters")
public class CharacterController {
	
	// === Instanciamos SERVICE ===
	@Autowired
	private CharacterService charServ;
	
	
	// == GET ==		
	@GetMapping("/all")
	public ResponseEntity<List<CharacterBasicDTO>> getBasicCharacters(){
		List<CharacterBasicDTO> charDTO = charServ.getCharacterBasicList();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(charDTO);
	}
		
	@GetMapping("/details/{id}")
	public ResponseEntity<CharacterDTO> getDetailsById(@PathVariable Long id){
		CharacterDTO charDetails = charServ.getCharDetails(id);
		return ResponseEntity.status(HttpStatus.OK).body(charDetails);
	}
	
	// == POST ==	
	@PostMapping
	public ResponseEntity<CharacterDTO> postNewCharacter(@RequestBody CharacterDTO newChar){
		CharacterDTO createdChar = charServ.saveNewCharacter(newChar);
		return ResponseEntity.status(HttpStatus.OK).body(createdChar);
	}
	
	// == PUT ==	
	@PutMapping("/{id}")
	public ResponseEntity<CharacterDTO> editCharacter(@PathVariable Long id, @RequestBody CharacterDTO charToEdit){
		CharacterDTO editedChar = charServ.editCharacterById(id, charToEdit);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(editedChar);
	}
	
	// == DELETE ==	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		charServ.deleteCharacterById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	// == FILTERS ==
	@GetMapping()
	public ResponseEntity<List<CharacterDTO>> getDetailsByFilters(
				@RequestParam(required = false) String name,
				@RequestParam(required = false) Integer age,
				@RequestParam(required = false) Set<Long> movies
			){
		List<CharacterDTO> charList = charServ.getByFilters(name, age, movies);
		return ResponseEntity.status(HttpStatus.OK).body(charList);
	}
}
