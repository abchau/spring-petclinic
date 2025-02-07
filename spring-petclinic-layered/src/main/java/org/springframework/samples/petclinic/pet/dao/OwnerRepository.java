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
package org.springframework.samples.petclinic.pet.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.pet.model.Owner;

/**
 * Repository class for <code>Owner</code> jpa entity All method names are compliant with
 * Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author github.com/abchau
 */
public interface OwnerRepository extends Repository<Owner, Integer> {

	/**
	 * Retrieve {@link Owner}s from the data store by last name, returning all owners
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link Owner}s (or an empty Collection if none
	 * found)
	 */
	@Query("SELECT DISTINCT owner FROM Owner owner WHERE owner.lastName LIKE :lastName% ")
	// @EntityGraph(attributePaths = { "pets" })
	@EntityGraph(value = "Owner.pets")
	Page<Owner> findByLastName(@Param("lastName") String lastName, Pageable pageable);

	/**
	 * Retrieve an {@link Owner} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Owner} if found
	 */
	@Query("SELECT owner FROM Owner owner WHERE owner.id =:id")
	Owner findById(@Param("id") Integer id);

	/**
	 * Retrieve an {@link Owner} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Owner} if found
	 */
	@Query("SELECT owner FROM Owner owner WHERE owner.id =:id")
	@EntityGraph(attributePaths = { "pets" })
	Owner findWithPetsById(@Param("id") Integer id);

	/**
	 * Retrieve an {@link Owner} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Owner} if found
	 */
	@Query("SELECT owner FROM Owner owner WHERE owner.id =:id")
	@EntityGraph(attributePaths = { "pets", "pets.visits" })
	Owner findWithPetsAndVisitsById(@Param("id") Integer id);

	/**
	 * Save an {@link Owner} to the data store, either inserting or updating it.
	 * @param Owner the {@link Owner} to save
	 */
	@Modifying
	Owner save(Owner Owner);

	/**
	 * Returnes all the owners from data store
	 **/
	@Query("SELECT owner FROM Owner owner")
	@EntityGraph(value = "Owner.pets")
	Page<Owner> findAll(Pageable pageable);

}
