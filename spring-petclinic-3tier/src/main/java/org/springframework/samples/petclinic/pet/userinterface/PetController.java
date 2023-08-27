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
package org.springframework.samples.petclinic.pet.userinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import org.springframework.samples.petclinic.pet.application.OwnerService;
import org.springframework.samples.petclinic.pet.application.PetService;
import org.springframework.samples.petclinic.pet.domain.Pet;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author github.com/abchau
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets")
class PetController {

	private final PetService petService;

	private final OwnerService ownerService;

	@Autowired
	public PetController(PetService petService, OwnerService ownerService) {
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping("/new")
	public String initCreationForm(@PathVariable("ownerId") Integer ownerId, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new Pet());
		}
		model.put("mode", "new");
		model.put("owner", ownerService.findById(ownerId));
		model.put("types", petService.findPetTypes());
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid Pet pet, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes, @PathVariable("ownerId") Integer ownerId) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", pet);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/new";
		}

		petService.createPet(pet);

		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/{petId}/edit")
	public String initUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			ModelMap model) {
		if (model.get("form") == null) {
			Pet pet = petService.findById(petId);
			model.put("form", pet);
		}

		model.put("mode", "edit");
		model.put("owner", ownerService.findById(ownerId));
		model.put("types", petService.findPetTypes());
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/{petId}/edit")
	public String processUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			@Valid Pet pet, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", pet);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/{petId}/edit";
		}

		petService.updatePet(pet);
		redirectAttributes.addFlashAttribute("success", true);

		return "redirect:/owners/{ownerId}/pets/{petId}/edit";
	}

}
