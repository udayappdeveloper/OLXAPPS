package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.UserEntity;
import com.olx.entity.UserEntity_OLD;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	//List<UserEntity> findByUserNameAndPassowrd(String userName, String password);
	List<UserEntity> findByUsername(String userName);
}
