package com.olx.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AdvStatus;
import com.olx.dto.AdvertisesDto;
import com.olx.dto.Category;
import com.olx.service.LoginDeligate;
import com.olx.service.MasterDataDeligate;
import com.olx.service.OlxAdvertisementService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/advertise")
public class AdvertizeController {

	@Autowired
	OlxAdvertisementService service;

	@Autowired
	LoginDeligate loginDeliageteService;

	@Autowired
	MasterDataDeligate masterDeligateService;

	@PostMapping(value = "/advertise", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Posts New Advertise")
	public AdvertisesDto postsNewAdvertise(@RequestHeader("Authorization") String authToken,
			@RequestBody AdvertisesDto adv) {
		return service.postsNewAdvertise(authToken, adv);
	}

	@PostMapping(value = "/validate", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },

			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Posts New Advertise")
	public boolean validateJWTToken(@RequestHeader("Authorization") String authToken) {

		return loginDeliageteService.validateJWTToken(authToken);

	}

	@PutMapping(value = "/advertise/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Updates Existing Advertise")
	public AdvertisesDto updatesexistingAdvertise(@RequestHeader("Authorization") String authToken,
			@PathVariable("id") int advertisementId, @RequestBody AdvertisesDto advrt) {
		return service.updatesexistingAdvertise(authToken, advertisementId, advrt);
	}

	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Reads All Advertisements Posted By User")
	public List<AdvertisesDto> readsAllAdvertisementsPostedbyUser(@RequestHeader("Authorization") String authToken

	) {

		return service.readsAllAdvertisementsPostedbyUser(authToken);

	}

	@GetMapping(value = "/user/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = " Reads Specific Advertisement PosedBy Loggedin User")
	public AdvertisesDto readsSpecificAdvertisementPosedByLoggedinUser(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return service.readsSpecificAdvertisementPosedByLoggedinUser(authToken, id);

	}

	@DeleteMapping(value = "/user/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Deletes Specific Advertisement PostedBy LoggedIn User")
	public boolean deletesSpecificAdvertisementPostedByLoggedInUser(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return service.deletesSpecificAdvertisementPostedByLoggedInUser(authToken, id);

	}

	@GetMapping(value = "/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Return Advertise Details")
	public AdvertisesDto returnAdvertiseDetails(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return service.returnAdvertiseDetails(authToken, id);

	}

	@GetMapping(value = "/advertise/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Search Filter Service Ads")
	public List<AdvertisesDto> searchAdvertisementsBasedUpOnGivenFilterCriteria(
			@RequestParam(name = "searchText", required = false) String searchText,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "postedBy", required = false) String postedBy,
			@RequestParam(name = "dateCondition", required = false) String dateCondition,
			@RequestParam(name = "onDate", required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)  LocalDate onDate,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false)  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate toDate,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "startIndex", required = false) Integer startIndex,
			@RequestParam(name = "records", required = false) Integer numOfRecords) {

		return service.searchAdvertisementsBasedUpOnGivenFilterCriteria(searchText, categoryId, postedBy, dateCondition,
				onDate, fromDate, toDate, sortBy, startIndex, numOfRecords);

	}

	@GetMapping(value = "/advertise/search", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Advertisement Search")
	public List<AdvertisesDto> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			@RequestParam(name = "searchText", required = false) String searchText) {

		return service.searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(searchText);
	}

	// get all categories

	@GetMapping(value = "/get/cat", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

	@ApiOperation(value = "Advertisement Search")
	public List<Category> mastercatgoriesSatus() {

		return masterDeligateService.getAllCategories();
	}

	@GetMapping(value = "/get/advstatus", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })

	@ApiOperation(value = "Advertisement Search")
	public List<AdvStatus> masterAdvStayus() {

		return masterDeligateService.getAllAdvertisesStatus();
	}

}
