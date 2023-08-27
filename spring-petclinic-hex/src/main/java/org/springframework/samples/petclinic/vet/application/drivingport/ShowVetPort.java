package org.springframework.samples.petclinic.vet.application.drivingport;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;

public interface ShowVetPort {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
