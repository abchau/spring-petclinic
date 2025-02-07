/*
 * Copyright 2023- the original author or authors.
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

import java.util.List;

/**
 * A DDD Repository (Don't confuse with Spring Data Repository)
 * A DDD Domain Service
 * @author github.com/abchau
 */
public interface PetRepository {

	public Pet create(Pet pet);

	public Pet update(Pet pet);

	public Pet findById(Integer petId);

	public Pet findWithVisitsById(Integer petId);

	public List<Pet> findAllByOwnerId(Integer ownerId);

	public List<PetType> findPetTypes();

}
