package org.springframework.samples.petclinic.vet.application.drivenport;

import java.util.List;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.samples.petclinic.vet.application.Vet;
import org.springframework.samples.petclinic.vet.application.Vet.PaginatedVet;

/**
 * Domain SPI
 *
 * @author github.com/abchau
 */
@SecondaryPort
public interface LoadVetPort {

	public List<Vet> findAll();

	public PaginatedVet findPaginatedVet(int page);

}
