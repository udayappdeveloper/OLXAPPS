package com.olx.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olx.dto.AdvStatus;
import com.olx.dto.Category;
import com.olx.exception.ServiceNotAvailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MasterDataDeligateImpl implements MasterDataDeligate {

	@Autowired
	RestTemplate restTemplate;

	@Override
	@CircuitBreaker(name = "CATEGORY-CIRCUIT-BREAKER", fallbackMethod = "fallBackGetAllCategories")
	public List<Category> getAllCategories() {
		ResponseEntity<Category[]> response = restTemplate
				.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/category", Category[].class);
		Category[] categoriesArray = response.getBody();
		List<Category> categoriesList = Arrays.asList(categoriesArray);
		return categoriesList;
	}

	public List<Category> fallBackGetAllCategories(Throwable ex) {
		throw new ServiceNotAvailableException();
	}

	@Override
	@CircuitBreaker(name = "ADVERTISE-CIRCUIT-BREAKER", fallbackMethod = "fallBackGetAllAdvertisesStatus")
	public List<AdvStatus> getAllAdvertisesStatus() {
		ResponseEntity<AdvStatus[]> response = restTemplate
				.getForEntity("http://API-GATEWAY/olx/masterdata/advertise/status", AdvStatus[].class);
		AdvStatus[] advStatusArray = response.getBody();
		List<AdvStatus> advStatusList = Arrays.asList(advStatusArray);
		return advStatusList;
	}

	public List<AdvStatus> fallBackGetAllAdvertisesStatus(Throwable ex) {
		throw new ServiceNotAvailableException();
	}

}
