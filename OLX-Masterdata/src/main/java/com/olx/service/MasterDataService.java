package com.olx.service;

import java.util.List;

import olx.dto.Category;
import olx.dto.AdvStatus;

public interface MasterDataService {
	public List<Category> returnAllAdvertisementCategories();

	public List<AdvStatus> returnsAllPossibleAdvertiseStatus();

}
