package com.olx.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.AdvStatus;
import com.olx.dto.AdvertisesDto;
import com.olx.dto.Category;
import com.olx.entity.AdvertisesEntity;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.repository.AdertisesRepository;
import com.olx.utility.LocalDateAttributeConverter;
import com.olx.utility.Utility;

@Service
public class OlxAdvertismentServiceImpl implements OlxAdvertisementService {

	@Autowired
	private AdertisesRepository advertiseRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	LoginDeligate loginDeligate;

	@Autowired
	MasterDataDeligate masterDataDeligate;

	@Autowired
	Utility utility;
	@Autowired
	AdertisesRepository adRespository;
	@Autowired
	LocalDateAttributeConverter dateCoverter;

	@Override
	public AdvertisesDto postsNewAdvertise(String authToken, AdvertisesDto advrt) {

		if (loginDeligate.validateJWTToken(authToken)) {
			String userName = loginDeligate.getUserInformation(authToken);
			List<Category> catList = masterDataDeligate.getAllCategories();
			List<AdvStatus> advStatusList = masterDataDeligate.getAllAdvertisesStatus();
			int catId = advrt.getCategoryId();
			String catName = utility.getCategoryNameFromMap(catList, catId);
			int statusId = advrt.getStatusId();
			String status = utility.getAdvertiseStatusFromMap(advStatusList, statusId);
			advrt.setUsername(userName);
			advrt.setCategory(catName);
			advrt.setStatus(status);
			advrt.setCreated_date(LocalDate.now());
			advrt.setModified_date(LocalDate.now());
			advrt.setActive(1);

			AdvertisesEntity advEntity = utility.convertAdvertiseDtoToEntity(advrt);
			adRespository.save(advEntity);
			advrt.setId(advEntity.getId());
			return advrt;

		} else {

			throw new InvalidAuthTokenException("Invalid Auth token");
		}
	}

	@Override
	public AdvertisesDto updatesexistingAdvertise(String authToken, int advertisementId, AdvertisesDto advrt) {
		if (loginDeligate.validateJWTToken(authToken)) {
			String userName = loginDeligate.getUserInformation(authToken);
			List<Category> catList = masterDataDeligate.getAllCategories();
			List<AdvStatus> advStatusList = masterDataDeligate.getAllAdvertisesStatus();
			int catId = advrt.getCategoryId();
			String catName = utility.getCategoryNameFromMap(catList, catId);
			int statusId = advrt.getStatusId();
			String status = utility.getAdvertiseStatusFromMap(advStatusList, statusId);

			advrt.setUsername(userName);
			advrt.setCategory(catName);
			advrt.setStatus(status);
			advrt.setModified_date(LocalDate.now());
			advrt.setActive(1);
			advrt.setId(advertisementId);

			AdvertisesEntity advEntity = utility.convertAdvertiseDtoToEntity(advrt);
			adRespository.save(advEntity);

			return advrt;

		} else {

			throw new InvalidAuthTokenException("Invalid Auth token");
		}

	}

	@Override
	public List<AdvertisesDto> readsAllAdvertisementsPostedbyUser(String authToken) {
		if (loginDeligate.validateJWTToken(authToken)) {

			String userName = loginDeligate.getUserInformation(authToken);

			List<AdvertisesEntity> advertisesEntityList = advertiseRepository.findByUsername(userName);
			List<AdvertisesDto> advertisesDtos = getDtosListFromEntities(advertisesEntityList);
			return advertisesDtos;
		} else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}

	}

	@Override
	public AdvertisesDto readsSpecificAdvertisementPosedByLoggedinUser(String authToken, int advertisementId) {

		if (loginDeligate.validateJWTToken(authToken)) {

			String userName = loginDeligate.getUserInformation(authToken);

			Optional<AdvertisesEntity> optionalEntity = advertiseRepository.findByUsernameAndId(userName,
					advertisementId);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity advEntity = optionalEntity.get();
				List<Category> catList = masterDataDeligate.getAllCategories();
				List<AdvStatus> advStatusList = masterDataDeligate.getAllAdvertisesStatus();
				int catId = advEntity.getCategory();
				String catName = utility.getCategoryNameFromMap(catList, catId);

				int statusId = advEntity.getStatus();
				String status = utility.getAdvertiseStatusFromMap(advStatusList, statusId);

				AdvertisesDto dto = convertToDto(advEntity);
				dto.setCategoryId(statusId);
				dto.setCategory(catName);
				dto.setStatus(status);

				return dto;
			} else {
				return null;
			}

		}

		else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}

	}

	@Override
	public boolean deletesSpecificAdvertisementPostedByLoggedInUser(String authToken, int advertisementId) {

		if (loginDeligate.validateJWTToken(authToken)) {

			Optional<AdvertisesEntity> optionalEntity = advertiseRepository.findById(advertisementId);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity advEntity = optionalEntity.get();
				advertiseRepository.delete(advEntity);
				return true;
			} else {
				return false;
			}

		}

		else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}

	}

	@Override
	public AdvertisesDto returnAdvertiseDetails(String authToken, int advertisementId) {

		if (loginDeligate.validateJWTToken(authToken)) {
			Optional<AdvertisesEntity> optionalEntity = advertiseRepository.findById(advertisementId);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity advEntity = optionalEntity.get();
				return convertToDto(advEntity);
			} else {
				return null;
			}

		} else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}

	@Override
	public List<AdvertisesDto> searchAdvertisementsBasedUpOnGivenFilterCriteria(String searchText, int categoryId,
			int userId, String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy,
			int sortIndex, int numOfRecords) {
		return null;
	}

	@Override
	public List<AdvertisesDto> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			String searchText) {
		List<AdvertisesEntity> advertisesEntityList = advertiseRepository.findByText(searchText);
		List<AdvertisesDto> advertisesDtos = getDtosListFromEntities(advertisesEntityList);
		return advertisesDtos;
	}

	/*
	 * 
	 * Entity to DTO Converter
	 */

	private AdvertisesDto convertToDto(AdvertisesEntity advertisesEntity) {
		AdvertisesDto advDto = modelMapper.map(advertisesEntity, AdvertisesDto.class);
		try {
			advDto.setModified_date(
					advertisesEntity.getModified_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		} catch (Exception e) {

		}
		try {
			advDto.setCreated_date(
					advertisesEntity.getCreated_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return advDto;
	}

	/*
	 * 
	 * DTO to Entity Converter
	 */

	private AdvertisesEntity convertToEntity(AdvertisesDto advertisesDto) {
		AdvertisesEntity advertisesEntity = modelMapper.map(advertisesDto, AdvertisesEntity.class);
		advertisesEntity.setModified_date(dateCoverter.convertToDatabaseColumn(advertisesDto.getModified_date()));
		advertisesEntity.setCreated_date(dateCoverter.convertToDatabaseColumn(advertisesDto.getCreated_date()));
		return advertisesEntity;
	}

	private List<AdvertisesDto> getDtosListFromEntities(List<AdvertisesEntity> advertisesEntitiesList) {
		List<AdvertisesDto> adverDtosList = new ArrayList<AdvertisesDto>();

		List<Category> catList = masterDataDeligate.getAllCategories();
		List<AdvStatus> advStatusList = masterDataDeligate.getAllAdvertisesStatus();
		for (AdvertisesEntity advEntity : advertisesEntitiesList) {

			int catId = advEntity.getCategory();
			String catName = utility.getCategoryNameFromMap(catList, catId);

			int statusId = advEntity.getStatus();
			String status = utility.getAdvertiseStatusFromMap(advStatusList, statusId);
			AdvertisesDto advDto = modelMapper.map(advEntity, AdvertisesDto.class);

			advDto.setStatus(status);
			advDto.setCategory(catName);
			try {
				advDto.setModified_date(
						advEntity.getModified_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

			} catch (Exception e) {

			}
			try {
				advDto.setCreated_date(
						advEntity.getCreated_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

			} catch (Exception e) {
				e.printStackTrace();
			}
			adverDtosList.add(advDto);
		}
		return adverDtosList;
	}

}
