package org.springframework.samples.petclinic.pet.application;

// no source code dependency on infrastructure
import org.springframework.samples.petclinic.pet.domain.visit.AddVisitUseCase;
import org.springframework.samples.petclinic.pet.domain.visit.ShowVisitUseCase;
import org.springframework.samples.petclinic.pet.domain.visit.Visit;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitFacade {

	private final ShowVisitUseCase showVisitUseCase;

	private final AddVisitUseCase addVisitUseCase;

	@Autowired
	public VisitFacade(ShowVisitUseCase showVisitUseCase, AddVisitUseCase addVisitUseCase) {
		this.showVisitUseCase = showVisitUseCase;
		this.addVisitUseCase = addVisitUseCase;
	}

	public Visit addVisit(CreateVisitCommand command) {
		Visit visit = new Visit();
		visit.setPetId(command.getPetId());
		visit.setDate(command.getDate());
		visit.setDescription(command.getDescription());

		return addVisitUseCase.addVisit(visit);
	}

	public List<Visit> findVisits(Integer petId) {
		return showVisitUseCase.findVisits(petId);
	}

	// input requirement could change from facade to facade. it is unique to this facade
	// hence public static inner class
	public static class CreateVisitCommand {

		private Integer petId;

		private LocalDate date;

		private String description;

		public Integer getPetId() {
			return petId;
		}

		public void setPetId(Integer petId) {
			this.petId = petId;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "CreateVisitCommand [petId=" + petId + ", date=" + date + ", description=" + description + "]";
		}

	}

}
