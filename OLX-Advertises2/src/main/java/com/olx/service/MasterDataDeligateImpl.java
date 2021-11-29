package com.olx.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olx.dto.AdvStatus;
import com.olx.dto.Category;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MasterDataDeligateImpl implements MasterDataDeligate {

	@Autowired
	RestTemplate restTemplate;

	@Override
	@CircuitBreaker(name = "CATEGORY-CIRCUIT-BREAKER", fallbackMethod = "fallBackGetAllCategories")
	public List<Category> getAllCategories() {
		// List<Map> list =
		// restTemplate.getForObject("http://masterdata-service/olx/advertise/category",
		// List.class);

		ResponseEntity<Category[]> response = restTemplate
				.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/category", Category[].class);
		Category[] categoriesArray = response.getBody();

		List<Category> categoriesList = Arrays.asList(categoriesArray);

		// List<Map> list =
		// restTemplate.getForObject("http://API-GATEWAY/olx/masterdata/advertise/category",
		// List.class);

		return categoriesList;
	}

	public List<Category> fallBackGetAllCategories(Throwable ex) {
		System.out.println("MasterData call failed:" + ex);
		return null;
	}

	@Override
	@CircuitBreaker(name = "ADVERTISE-CIRCUIT-BREAKER", fallbackMethod = "fallBackGetAllAdvertisesStatus")
	public List<AdvStatus> getAllAdvertisesStatus() {

		// List<Map> list =
		// restTemplate.getForObject("http://API-GATEWAY/olx/masterdata/advertise/status",
		// List.class);

		ResponseEntity<AdvStatus[]> response = restTemplate
				.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/status", AdvStatus[].class);
		AdvStatus[] advStatusArray = response.getBody();
		List<AdvStatus> advStatusList = Arrays.asList(advStatusArray);

		return advStatusList;
	}

	public List<AdvStatus> fallBackGetAllAdvertisesStatus(Throwable ex) {
		System.out.println("MasterData call failed:" + ex);
		return null;
	}

	/*
	 * @Override public List<Map> getAllMapCategories() {
	 * 
	 * List<Map> list = restTemplate.getForObject(
	 * "http://API-GATEWAY/olx/masterdata/advertise/category", List.class);
	 * 
	 * return list; }
	 * 
	 * @Override public List<Map> getAllMapAdvertisesStatus() { List<Map> list =
	 * restTemplate.getForObject(
	 * "http://API-GATEWAY/olx/masterdata/advertise/status", List.class); return
	 * list; }
	 */

}
