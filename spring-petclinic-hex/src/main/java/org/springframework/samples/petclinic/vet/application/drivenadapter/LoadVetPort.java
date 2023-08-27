package org.springframework.samples.petclinic.vet.application.drivenadapter;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;

// SPI
public interface LoadVetPort {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
