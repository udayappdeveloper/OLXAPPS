package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.AdvStatus;
import com.olx.dto.AdvertisesDto;
import com.olx.dto.Category;
import com.olx.entity.AdvertisesEntity;
import com.olx.exception.AdNotFoundException;
import com.olx.exception.InvalidAdvertiseIdException;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.repository.AdertisesRepository;
import com.olx.utility.LocalDateAttributeConverter;
import com.olx.utility.Utility;
import com.olx.utility.Constants;

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

	@Autowired
	EntityManager entityManager;

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
			advrt.setCreatedDate(LocalDate.now());
			advrt.setModifiedDate(LocalDate.now());
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
			advrt.setModifiedDate(LocalDate.now());
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
				throw new AdNotFoundException();
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
				throw new AdNotFoundException();
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
				throw new InvalidAdvertiseIdException(advertisementId + "");
			}

		} else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}

	@Override
	public List<AdvertisesDto> searchAdvertisementsBasedUpOnGivenFilterCriteria(String searchText, Integer categoryId,
			String postedBy, String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate,
			String sortBy, Integer startIndex, Integer numOfRecords) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertisesEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertisesEntity.class);
		Root<AdvertisesEntity> rootEntity = criteriaQuery.from(AdvertisesEntity.class);
		// criteriaQuery.select(rootEntity);
		List<Predicate> predicateList = new ArrayList<>();
		if (searchText != null && !searchText.isEmpty()) {
			Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
			predicateList.add(titlePredicate);
		}
		if (postedBy != null && postedBy.isEmpty()) {
			Predicate postedByPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), postedBy);
			predicateList.add(postedByPredicate);
		}

		if (categoryId != null) {
			Predicate categoryPredicate = criteriaBuilder.equal(rootEntity.get("category"), categoryId);
			predicateList.add(categoryPredicate);
		}

		// Predicate dateConditionPredicate = null;
		if (dateCondition != null) {
			switch (dateCondition) {
			case Constants.EQUAL:
				Predicate dateConditionPredicate1 = criteriaBuilder.equal(rootEntity.get("createdDate"), onDate);
				predicateList.add(dateConditionPredicate1);
				break;

			case Constants.GREATERTHAN:
				Predicate dateConditionPredicate2 = criteriaBuilder.greaterThan(rootEntity.get("createdDate"),
						fromDate);
				predicateList.add(dateConditionPredicate2);
				break;

			case Constants.LESSTHAN:
				Predicate dateConditionPredicate3 = criteriaBuilder.lessThan(rootEntity.get("createdDate"), fromDate); //
				predicateList.add(dateConditionPredicate3);
				break;

			case Constants.BETWEEN:
				Predicate dateConditionPredicate4 = criteriaBuilder.between(rootEntity.get("createdDate"), fromDate,
						toDate); //
				predicateList.add(dateConditionPredicate4);
				break;

			default:
				break;
			}

		}

		Predicate[] arr = new Predicate[predicateList.size()];
		criteriaQuery.where(predicateList.toArray(arr));
		if (sortBy != null && sortBy.equalsIgnoreCase("ASC")) {
			criteriaQuery.orderBy(criteriaBuilder.asc(rootEntity.get("createdDate")));
		}
		if (sortBy != null && sortBy.equalsIgnoreCase("DESC")) {
			criteriaQuery.orderBy(criteriaBuilder.desc(rootEntity.get("createdDate")));
		}

		TypedQuery<AdvertisesEntity> query = entityManager.createQuery(criteriaQuery);

		if (startIndex != null && numOfRecords != null) {
			query.setFirstResult(startIndex);
			query.setMaxResults(numOfRecords);
		}
		List<AdvertisesEntity> advertiseEntityList = query.getResultList();
		List<AdvertisesDto> advertisesDtos = getDtosListFromEntities(advertiseEntityList);
		return advertisesDtos;
	}

	@Override
	public List<AdvertisesDto> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			String searchText) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertisesEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertisesEntity.class);
		Root<AdvertisesEntity> rootEntity = criteriaQuery.from(AdvertisesEntity.class);
		List<Predicate> predicateList = new ArrayList<>();

		Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
		Predicate descriptionPredicate = criteriaBuilder.like(rootEntity.get("description"), "%" + searchText + "%");

		Predicate postedPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), "%" + searchText + "%"); // title=searchText
		Predicate userNamePredicate = criteriaBuilder.like(rootEntity.get("username"), "%" + searchText + "%");
		// Predicate categoryPredicate =
		// criteriaBuilder.equal(rootEntity.get("category"), searchText);

		Predicate postedByPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), searchText);
		Predicate predicateAnd1 = criteriaBuilder.or(titlePredicate, postedPredicate, postedByPredicate,
				userNamePredicate, descriptionPredicate);
		predicateList.add(predicateAnd1);
		Predicate finalPredicate = criteriaBuilder.or(titlePredicate, postedPredicate, userNamePredicate,
				descriptionPredicate);
		criteriaQuery.where(finalPredicate);
		TypedQuery<AdvertisesEntity> query = entityManager.createQuery(criteriaQuery);
		List<AdvertisesEntity> advertiseEntityList = query.getResultList();
		List<AdvertisesDto> advertisesDtos = getDtosListFromEntities(advertiseEntityList);
		return advertisesDtos;
	}

	/*
	 * 
	 * Entity to DTO Converter
	 */

	private AdvertisesDto convertToDto(AdvertisesEntity advertisesEntity) {
		AdvertisesDto advDto = modelMapper.map(advertisesEntity, AdvertisesDto.class);
//		try {
//			advDto.setModifiedDate(
//					advertisesEntity.getModifiedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//
//		} catch (Exception e) {
//
//		}
//		try {
//			advDto.setCreatedDate(
//					advertisesEntity.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return advDto;
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
			advDto.setCategoryId(catId);
			advDto.setActive(advEntity.getStatus());
			advDto.setStatus(status);
			advDto.setCategory(catName);
			adverDtosList.add(advDto);
		}
		return adverDtosList;
	}

}
