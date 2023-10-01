package org.springframework.samples.petclinic.vet.application.drivingport;

import java.util.List;

import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.samples.petclinic.vet.application.Vet;
import org.springframework.samples.petclinic.vet.application.Vet.PaginatedVet;

/**
 * Domain API
 *
 * @author github.com/abchau
 */
@PrimaryPort
public interface ShowVetPort {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
