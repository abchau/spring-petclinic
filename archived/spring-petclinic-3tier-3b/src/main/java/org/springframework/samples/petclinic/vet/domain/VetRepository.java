package org.springframework.samples.petclinic.vet.domain.vet;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.vet.Vet.PaginatedVet;

/**
 * A DDD Repository
 */
public interface VetRepository {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
