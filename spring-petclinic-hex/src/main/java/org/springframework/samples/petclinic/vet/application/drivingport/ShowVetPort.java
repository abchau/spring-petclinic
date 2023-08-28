package org.springframework.samples.petclinic.vet.application.drivingport;

import java.util.List;

import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
public interface ShowVetPort {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
