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
package org.springframework.samples.petclinic.pet.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.pet.domain.pet.PetType;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>PetTypeEntity</code> jpa entity All method names are
 * compliant with Spring Data naming conventions so this interface can easily be extended
 * for Spring Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface PetEntityRepository extends Repository<PetEntity, Integer> {

	/**
	 * Save an {@link PetEntity} to the data store, either inserting or updating it.
	 * @param petEntity the {@link PetEntity} to save
	 */
	@Modifying
	PetEntity save(PetEntity petEntity);

	/**
	 * Retrieve an {@link Owner} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Owner} if found
	 */
	PetEntity findById(Integer id);
	
	/**
	 * Retrieve an {@link Owner} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Owner} if found
	 */
	@EntityGraph(value = "PetEntity.visits")
	PetEntity findWithVisitsById(Integer id);
	
	/**
	 * Retrieve all {@link PetType}s from the data store.
	 * @return a Collection of {@link PetType}s.
	 */
	@Query("SELECT pet FROM PetEntity pet WHERE pet.ownerId = :ownerId ORDER BY pet.name")
	List<PetEntity> findAllByOwnerId(Integer ownerId);

}
