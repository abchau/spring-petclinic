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
package org.springframework.samples.petclinic.pet.drivingadapter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.samples.petclinic.pet.application.drivingport.AddVisitPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowPetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowVisitPort;
import org.springframework.samples.petclinic.pet.domain.Visit;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author github.com/abchau
 */
@Controller
/*final*/ class VisitController {

	private final ShowOwnerPort showOwnerPort;

	private final ShowPetPort showPetPort;

	private final ShowVisitPort showVisitPort;

	private final AddVisitPort addVisitPort;

	@Autowired
	public VisitController(ShowOwnerPort showOwnerPort, ShowPetPort showPetPort, ShowVisitPort showVisitPort,
			AddVisitPort addVisitPort) {
		this.showOwnerPort = showOwnerPort;
		this.showPetPort = showPetPort;
		this.showVisitPort = showVisitPort;
		this.addVisitPort = addVisitPort;
	}

	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable Integer ownerId, @PathVariable Integer petId, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new Visit());
		}
		model.put("mode", "new");
		model.put("owner", showOwnerPort.findById(ownerId));
		model.put("pet", showPetPort.findWithVisitsById(petId));
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@PathVariable Integer ownerId, @PathVariable Integer petId, @Valid Visit form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/{petId}/visits/new";
		}

		addVisitPort.addVisit(form);

		return "redirect:/owners/{ownerId}";
	}

}
