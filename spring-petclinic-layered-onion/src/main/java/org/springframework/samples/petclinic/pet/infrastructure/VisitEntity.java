/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.pet.infrastructure;

import java.time.LocalDate;
import java.util.function.Function;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.pet.domain.model.Visit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Simple hibernate entity representing a visit table.
 *
 * @author Ken Krebs
 * @author Dave Syer
 * @author github.com/abchau
 */
@Entity
@Table(name = "visits")
class VisitEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@Column(name = "pet_id")
	private Integer petId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id", updatable = false, insertable = false)
	private PetEntity pet;

	@Column(name = "description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "VisitEntity [id=" + id + ", date=" + date + ", petId=" + petId + ", description=" + description + "]";
	}

	static Function<VisitEntity, Visit> translateToDomainModel = (visitEntity) -> {
		Visit visit = new Visit();
		visit.setId(visitEntity.getId());
		visit.setPetId(visitEntity.getPetId());
		visit.setDate(visitEntity.getDate());
		visit.setDescription(visitEntity.getDescription());

		return visit;
	};

	static Function<Visit, VisitEntity> translateToPersistenceModel = (visit) -> {
		VisitEntity visitEntity = new VisitEntity();
		visitEntity.setId(visit.getId());
		visitEntity.setPetId(visit.getPetId());
		visitEntity.setDate(visit.getDate());
		visitEntity.setDescription(visit.getDescription());

		return visitEntity;
	};

}
