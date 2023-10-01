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
package org.springframework.samples.petclinic.pet.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.

 * @author github.com/abchau
 */
public final class Owner {

	private Integer id;

	private String firstName;

	private String lastName;

	private String address;

	private String city;

	private String telephone;

	private List<Pet> pets;

	public Owner() {
		this.pets = Collections.emptyList();
	}

	public String getPetNames() {
		return this.pets.stream().map(Pet::getName).collect(Collectors.joining(", "));
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Pet> getPets() {
		return this.pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.append("pets", this.pets)
			.toString();
	}

	/**
	 * just a sloppy demo to show we can choose to get rid of dependency on
	 * <code>org.springframework.data.domain</code> outside <code>infrastructure</code>
	 * package.
	 */
	public static class PaginatedOwner {

		private int totalPages;

		private long totalElements;

		private List<Owner> content;

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

		public List<Owner> getContent() {
			return content;
		}

		public void setContent(List<Owner> content) {
			this.content = content;
		}

	}

}
