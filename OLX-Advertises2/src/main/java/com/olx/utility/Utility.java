package com.olx.utility;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.olx.dto.AdvStatus;
import com.olx.dto.AdvertisesDto;
import com.olx.dto.Category;
import com.olx.entity.AdvertisesEntity;

public class Utility {

	@Autowired

	ModelMapper modelMapper;

	@Autowired
	LocalDateAttributeConverter dateConverter;

	public String getCategoryNameFromMap(List<Category> list, int catId) {
		String categoryName = "";
		for (Category cat : list) {
			if (cat.getId() == catId) {
				categoryName = cat.getCatgory();
				break;
			}

		}

		return categoryName;
	}

	public String getAdvertiseStatusFromMap(List<AdvStatus> list, int statusId) {
		String status = "";
		for (AdvStatus advStatus : list) {

			if (advStatus.getId() == statusId) {
				status = advStatus.getStatus();
				break;
			}
		}
		return status;
	}

	public AdvertisesEntity convertAdvertiseDtoToEntity(AdvertisesDto dto) {
		TypeMap<AdvertisesDto, AdvertisesEntity> typeMap = this.modelMapper.typeMap(AdvertisesDto.class,
				AdvertisesEntity.class);
		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCategoryId(), AdvertisesEntity::setCategory);
			mapper.map(source -> source.getStatusId(), AdvertisesEntity::setStatus);

//			mapper.map(source -> dateConverter.convertToDatabaseColumn(source.getCreatedDate()),
//					AdvertisesEntity::setCreatedDate);
//			mapper.map(source -> dateConverter.convertToDatabaseColumn(source.getModifiedate()),
//					AdvertisesEntity::setModifiedDate);
			mapper.map(source -> source.getUsername(), AdvertisesEntity::setPostedBy);

		});
		AdvertisesEntity advertisesEntity = this.modelMapper.map(dto, AdvertisesEntity.class);
		return advertisesEntity;

	}

}
