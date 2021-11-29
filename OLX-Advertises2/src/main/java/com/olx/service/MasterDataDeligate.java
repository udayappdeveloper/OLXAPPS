package com.olx.service;

import java.util.List;
import java.util.Map;

import com.olx.dto.AdvStatus;
import com.olx.dto.Category;

public interface MasterDataDeligate {

	public List<Category> getAllCategories();

	public List<AdvStatus> getAllAdvertisesStatus();
	
	/*
	 * public List<Map> getAllMapCategories();
	 * 
	 * public List<Map> getAllMapAdvertisesStatus();
	 */

}
