/*
 * Copyright 2012-2019 the original author or authors.
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
package org.springframework.samples.petclinic.visit.userinterface;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.owner.application.OwnerFacade;
import org.springframework.samples.petclinic.pet.application.PetFacade;
import org.springframework.samples.petclinic.visit.application.VisitFacade;
import org.springframework.samples.petclinic.visit.application.VisitFacade.CreateVisitCommand;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author github.com/abchau
 */
@Controller
class VisitController {

	private final VisitFacade visitFacade;

	private final PetFacade petFacade;

	private final OwnerFacade ownerFacade;

	public VisitController(VisitFacade visitFacade, PetFacade petFacade, OwnerFacade ownerFacade) {
		this.visitFacade = visitFacade;
		this.petFacade = petFacade;
		this.ownerFacade = ownerFacade;
	}

	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable Integer ownerId, @PathVariable Integer petId, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new CreateVisitForm());
		}
		model.put("mode", "new");
		model.put("owner", ownerFacade.findById(ownerId));
		model.put("pet", petFacade.findWithVisitsById(petId));
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@PathVariable Integer ownerId, @PathVariable Integer petId,
			@Valid CreateVisitForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/{petId}/visits/new";
		}

		// not demostrate extra validation here

		CreateVisitCommand command = new CreateVisitCommand();
		// demostrates an HTML form doesn't always contain all information you need to
		// proceed. Nonetheless you have to collect all infromation and asseble an command
		// to pass it to application layer instead of creating magic side-effect call
		// inside application layer. e.g. user information, ip address, etc
		command.setPetId(petId);
		// (1) translate concept; (2) proper location to parse stuff based on locale
		command.setDate(LocalDate.parse(form.getDate()));
		command.setDescription(form.getDescription());

		visitFacade.addVisit(command);

		// NOTE: should validate result here

		return "redirect:/owners/{ownerId}";
	}

	private static class CreateVisitForm {

		// an concept in HTML form might be in a form different from the corresponding
		// business concept
		@NotEmpty
		private String date;

		@NotEmpty
		private String description;

		public String getDate() {
			return this.date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "CreateVisitForm [date=" + date + ", description=" + description + "]";
		}

	}

}
