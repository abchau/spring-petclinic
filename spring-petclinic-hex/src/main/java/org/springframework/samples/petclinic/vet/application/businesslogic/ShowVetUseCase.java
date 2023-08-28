package org.springframework.samples.petclinic.vet.application.businesslogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.vet.application.drivenadapter.LoadVetPort;
import org.springframework.samples.petclinic.vet.application.drivingport.ShowVetPort;
import org.springframework.samples.petclinic.vet.domain.Vet;
import org.springframework.samples.petclinic.vet.domain.Vet.PaginatedVet;
import org.springframework.stereotype.Component;

@Component
class ShowVetUseCase implements ShowVetPort {

	private final LoadVetPort loadVetPort;

	@Autowired
	public ShowVetUseCase(LoadVetPort loadVetPort) {
		this.loadVetPort = loadVetPort;
	}

	@Override
	public List<Vet> findAll() {
		return loadVetPort.findAll();
	}

	@Override
	public PaginatedVet findPaginatedVet(int page) {
		return loadVetPort.findPaginatedVet(page);
	}

}
