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

	public List<AdvertisesDto> searchAdvertisementsBasedUpOnGivenFilterCriteria(String searchText, int categoryId,
			int userId, String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy,
			int sortIndex, int numOfRecords);

	public List<AdvertisesDto> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			String searchText);

}
