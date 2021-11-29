package com.olx.repository;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.AdvertisesEntity;

public interface AdertisesRepository extends JpaRepository<AdvertisesEntity, Integer> {

	public Optional<AdvertisesEntity> findByUsernameAndId(String userName, int id);

	public List<AdvertisesEntity> findByUsername(String userName);

	@Query("SELECT a FROM AdvertisesEntity a WHERE a.title LIKE %:searchText% or a.category LIKE %:searchText% or a.description LIKE %:searchText%  or a.price LIKE %:searchText% or a.posted_by LIKE %:searchText%"+
	" or a.created_date LIKE %:searchText% or a.modified_date LIKE %:searchText%")
	public List<AdvertisesEntity> findByText(@Param("searchText") String searchText);
}
