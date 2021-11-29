package com.olx.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADV_STATUS")
public class AdvStatusEntity {

	/*
	 * CREATE TABLE `ADV_STATUS` ( `id` int NOT NULL, `status` varchar(50) NOT NULL,
	 * PRIMARY KEY (`id`)
	 */

	@Id
	@GeneratedValue
	private int id;

	@Override
	public String toString() {
		return "AdvStatusEntity [id=" + id + ", status=" + status + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
}
