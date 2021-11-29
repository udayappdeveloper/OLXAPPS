package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.entities.AdvStatusEntity;
import com.olx.entities.CategoryEntity;
import com.olx.repository.AdvStatusRepository;
import com.olx.repository.CategoryRepository;

import olx.dto.AdvStatus;
import olx.dto.Category;

@Service
public class MasterDataServiceIml implements MasterDataService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	AdvStatusRepository advStatusRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Category> returnAllAdvertisementCategories() {
		/*
		 * List<Category> categories = new ArrayList<Category>(); categories.add(new
		 * Category(1, "Furniture")); categories.add(new Category(2, "Real Estate"));
		 */

		List<CategoryEntity> categoriesEntities = categoryRepository.findAll();
		return convertCatgeoryEntityListToDtoList(categoriesEntities);
	}

	@Override
	public List<AdvStatus> returnsAllPossibleAdvertiseStatus() {
		/*
		 * List<Status> statusList = new ArrayList<Status>(); statusList.add(new
		 * Status(1, "OPEN")); statusList.add(new Status(2, "CLOSED"));
		 */

		List<AdvStatusEntity> advStatusList = advStatusRepository.findAll();
		return convertAdvStatusEntityListToDtoList(advStatusList);

	}

	private List<Category> convertCatgeoryEntityListToDtoList(List<CategoryEntity> categoriesEntities) {
		List<Category> categoriesList = new ArrayList<>();

		for (CategoryEntity catEntity : categoriesEntities) {
			Category cat = modelMapper.map(catEntity, Category.class);
			cat.setCatgory(catEntity.getName());
			categoriesList.add(cat);
		}
		return categoriesList;
	}

	private List<AdvStatus> convertAdvStatusEntityListToDtoList(List<AdvStatusEntity> advoStatusEntities) {
		List<AdvStatus> advStatusList = new ArrayList<>();
		for (AdvStatusEntity advEntity : advoStatusEntities) {
			advStatusList.add(modelMapper.map(advEntity, AdvStatus.class));
		}
		return advStatusList;
	}

}
