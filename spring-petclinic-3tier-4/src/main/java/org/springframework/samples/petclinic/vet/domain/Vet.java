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
package org.springframework.samples.petclinic.vet.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
public class Vet {

	private Integer id;

	private String firstName;

	private String lastName;

	private List<Specialty> specialties;

	public Vet() {
		this.specialties = new ArrayList<>();
	}

	public int nrOfSpecialties() {
		return this.specialties.size();
	}
	
	public void addSpecialty(Specialty specialty) {
		this.specialties.add(specialty);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Specialty> getSpecialties() {
		return this.specialties;
	}

	public void setSpecialties(List<Specialty> specialties) {
		this.specialties = specialties;
	}

	/**
	 * just a sloppy demo to show we can choose to get rid of dependency on
	 * <code>org.springframework.data.domain</code> outside <code>infrastructure</code>
	 * package
	 */
	public static class PaginatedVet {

		private int totalPages;

		private long totalElements;

		private List<Vet> content;

		public boolean isEmpty() {
			return content == null || content.size() == 0;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public List<Vet> getContent() {
			return content;
		}

		public void setContent(List<Vet> content) {
			this.content = content;
		}

	}

}
