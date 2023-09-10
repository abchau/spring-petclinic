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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.samples.petclinic.pet.application.drivingport.CreatePetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowPetPort;
import org.springframework.samples.petclinic.pet.application.drivingport.UpdatePetPort;
import org.springframework.samples.petclinic.pet.domain.Pet;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author github.com/abchau
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets")
/*final*/ class PetController {

	private final ShowOwnerPort showOwnerPort;

	private final ShowPetPort showPetPort;

	private final CreatePetPort createPetPort;

	private final UpdatePetPort updatePetPort;

	@Autowired
	public PetController(ShowOwnerPort showOwnerPort, ShowPetPort showPetPort, CreatePetPort createPetPort,
			UpdatePetPort updatePetPort) {
		this.showOwnerPort = showOwnerPort;
		this.showPetPort = showPetPort;
		this.createPetPort = createPetPort;
		this.updatePetPort = updatePetPort;
	}

	@GetMapping("/new")
	public String initCreationForm(@PathVariable("ownerId") Integer ownerId, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new Pet());
		}
		model.put("mode", "new");
		model.put("owner", showOwnerPort.findById(ownerId));
		model.put("types", showPetPort.findPetTypes());
		
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid Pet form, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes, @PathVariable("ownerId") Integer ownerId) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			
			return "redirect:/owners/{ownerId}/pets/new";
		}

		createPetPort.createPet(form);

		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/{petId}/edit")
	public String initUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", showPetPort.findById(petId));
		}

		model.put("mode", "edit");
		model.put("owner", showOwnerPort.findById(ownerId));
		model.put("types", showPetPort.findPetTypes());

		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/{petId}/edit")
	public String processUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			@Valid Pet form, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			
			return "redirect:/owners/{ownerId}/pets/{petId}/edit";
		}

		if (petId != form.getId()) {
			redirectAttributes.addFlashAttribute("form", form);
			FieldError fieldError = new FieldError(Pet.class.getName(), "id", "gocha!");
			redirectAttributes.addFlashAttribute("errors", List.of(fieldError));
			
			return "redirect:/owners/{ownerId}/pets/{petId}/edit";
		}

		updatePetPort.updatePet(form);

		return "redirect:/owners/{ownerId}/pets/{petId}/edit";
	}

}
