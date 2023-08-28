package org.springframework.samples.petclinic.vet.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.application.ShowVetUseCase;
import org.springframework.samples.petclinic.vet.domain.LoadVetPort;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;
import org.springframework.stereotype.Component;

@Component
class ShowVetUseCase {

	private final LoadVetPort loadVetPort;

	@Autowired
	public ShowVetUseCase(LoadVetPort loadVetPort) {
		this.loadVetPort = loadVetPort;
	}

	public List<Vet> findAll() {
		return loadVetPort.findAll();
	}

	public PaginatedVet findPaginatedVet(int page) {
		return loadVetPort.findPaginatedVet(page);
	}

}
