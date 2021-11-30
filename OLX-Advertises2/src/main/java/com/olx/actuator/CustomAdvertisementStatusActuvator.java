package com.olx.actuator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import com.olx.dto.AdvertisesDto;

/*@Component
@Endpoint(id = "/advertiseStats")*/
public class CustomAdvertisementStatusActuvator {

	final List<AdvertisesDto> advertisementsList = new ArrayList<>();

	//@PostConstruct
	public void initialize() {

	}

	//@ReadOperation
	public List<AdvertisesDto> getAdvertises(@Selector String status) {

		return Collections.EMPTY_LIST;

	}

}
