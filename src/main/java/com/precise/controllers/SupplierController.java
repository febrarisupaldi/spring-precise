package com.precise.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.precise.dto.ResponseData;
import com.precise.dto.SearchData;
import com.precise.dto.SupplierData;
import com.precise.models.entities.Product;
import com.precise.models.entities.Supplier;
import com.precise.services.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierData supplierData, Errors errors){
		ResponseData<Supplier> responseData = new ResponseData<>();
		if(errors.hasErrors()) {
			for (ObjectError error: errors.getAllErrors()) {
				responseData.getMessages().add(error.getDefaultMessage());
			}
			responseData.setStatus(false);
			responseData.setPayload(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
		}
		
		Supplier supplier = modelMapper.map(supplierData, Supplier.class);
		
		responseData.setStatus(true);
		responseData.setPayload(supplierService.save(supplier));
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping
	public Iterable<Supplier> findAll(){
		return supplierService.findAll();
	}
	
	@GetMapping("/{id}")
	public Supplier findOne(@PathVariable("id") Long id) {
		return supplierService.findOne(id);
	}
	
	@PutMapping
	public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierData supplierData, Errors errors){
		ResponseData<Supplier> responseData = new ResponseData<>();
		if(errors.hasErrors()) {
			for (ObjectError error: errors.getAllErrors()) {
				responseData.getMessages().add(error.getDefaultMessage());
			}
			responseData.setStatus(false);
			responseData.setPayload(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
		}
		
		Supplier supplier = modelMapper.map(supplierData, Supplier.class);
		
		responseData.setStatus(true);
		responseData.setPayload(supplierService.save(supplier));
		return ResponseEntity.ok(responseData);
	}
	
	@PostMapping("/search/email")
	public Supplier findByEmail(@RequestBody SearchData searchData) {
		return supplierService.findByEmail(searchData.getSearchKey());
	}
	
	@PostMapping("/search/name")
	public List<Supplier> findByName(@RequestBody SearchData searchData) {
		return supplierService.findByName(searchData.getSearchKey());
	}
	
	@PostMapping("/search/namestartwith")
	public List<Supplier> findByNameStartWith(@RequestBody SearchData searchData) {
		return supplierService.findByNameStartWith(searchData.getSearchKey());
	}
	
	@PostMapping("/search/nameoremail")
	public List<Supplier> findByNameOrEmail(@RequestBody SearchData searchData) {
		return supplierService.findByNameOrEmail(searchData.getSearchKey(), searchData.getOtherSearchKey());
	}
}
