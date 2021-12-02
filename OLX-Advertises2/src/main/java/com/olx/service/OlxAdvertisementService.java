package com.olx.service;

import java.time.LocalDate;
import java.util.List;

import com.olx.dto.AdvertisesDto;

public interface OlxAdvertisementService {

	public AdvertisesDto postsNewAdvertise(String authToken,AdvertisesDto adv);

	public AdvertisesDto updatesexistingAdvertise(String authToken,int advertisementId, AdvertisesDto advrt);

	public List<AdvertisesDto> readsAllAdvertisementsPostedbyUser(String authToken);

	public AdvertisesDto readsSpecificAdvertisementPosedByLoggedinUser(String authToken,int id);

	public boolean deletesSpecificAdvertisementPostedByLoggedInUser(String authToken,int id);

	public AdvertisesDto returnAdvertiseDetails(String authToken,int id);

	public List<AdvertisesDto> searchAdvertisementsBasedUpOnGivenFilterCriteria(String searchText, Integer categoryId,
			String postedBy, String dateCondition, LocalDate onDate,LocalDate fromDate, LocalDate toDate, String sortBy,
			Integer sortIndex, Integer numOfRecords);
	
	



	public List<AdvertisesDto> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			String searchText);

}
