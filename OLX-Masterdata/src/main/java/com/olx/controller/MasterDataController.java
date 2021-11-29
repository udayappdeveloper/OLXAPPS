package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.service.MasterDataService;

import io.swagger.annotations.ApiOperation;
import olx.dto.Category;
import olx.dto.AdvStatus;

@RestController
@RequestMapping("olx/masterdata")
public class MasterDataController {

	@Autowired
	 MasterDataService masterDataService;

	// service no 5
	@GetMapping(value = "/advertise/category", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
			)
	@ApiOperation(value="To Get All the Categories")
	public List<Category> returnAllAdvertisementCategories () {
		return masterDataService.returnAllAdvertisementCategories();

	}

	// service no 6
	@GetMapping(value = "/advertise/status", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="To Get All the Advertisements Status")
	public List<AdvStatus> returnsAllPossibleAdvertiseStatus() {
		return masterDataService.returnsAllPossibleAdvertiseStatus();

	}

}
