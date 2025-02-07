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

import java.time.LocalDate;

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
import jakarta.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.pet.application.OwnerFacade;
import org.springframework.samples.petclinic.pet.application.PetFacade;
import org.springframework.samples.petclinic.pet.domain.model.Pet;
import org.springframework.samples.petclinic.pet.domain.model.PetType;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author github.com/abchau
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets")
 class PetController {

	private final PetFacade petFacade;

	private final OwnerFacade ownerFacade;

	@Autowired
	public PetController(PetFacade petFacade, OwnerFacade ownerFacade) {
		this.petFacade = petFacade;
		this.ownerFacade = ownerFacade;
	}

	@GetMapping("/new")
	public String initCreationForm(@PathVariable("ownerId") Integer ownerId, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", CreatePetForm.empty());
		}
		model.put("mode", "new");
		model.put("owner", ownerFacade.findById(ownerId));
		model.put("types", petFacade.findPetTypes());
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid CreatePetForm form, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes, @PathVariable("ownerId") Integer ownerId) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/new";
		}

		Pet pet = new Pet();
		pet.setOwnerId(ownerId);
		pet.setName(form.name());
		pet.setBirthDate(LocalDate.parse(form.birthDate()));
		pet.setType(PetType.of(form.typeId()));

		petFacade.createPet(pet);

		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/{petId}/edit")
	public String initUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			ModelMap model) {
		if (model.get("form") == null) {
			Pet pet = petFacade.findById(petId);
			model.put("form", UpdatePetForm.of(pet));
		}

		model.put("mode", "edit");
		model.put("owner", ownerFacade.findById(ownerId));
		model.put("types", petFacade.findPetTypes());
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/{petId}/edit")
	public String processUpdateForm(@PathVariable("ownerId") Integer ownerId, @PathVariable("petId") Integer petId,
			@Valid UpdatePetForm form, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			return "redirect:/owners/{ownerId}/pets/{petId}/edit";
		}

		Pet pet = new Pet();
		pet.setId(petId);
		pet.setOwnerId(ownerId);
		pet.setName(form.name());
		pet.setBirthDate(LocalDate.parse(form.birthDate()));
		pet.setType(PetType.of(form.typeId()));

		petFacade.updatePet(pet);
		redirectAttributes.addFlashAttribute("success", true);

		return "redirect:/owners/{ownerId}/pets/{petId}/edit";
	}

	private static record CreatePetForm(Integer typeId, @NotEmpty String name, @NotEmpty String birthDate) {
		public static CreatePetForm empty() {
			return new CreatePetForm(null, null, null);
		}
	}

	private static record UpdatePetForm(Integer id, Integer typeId, @NotEmpty String name, @NotEmpty String birthDate) {
		public static UpdatePetForm of(Pet pet) {
			return new UpdatePetForm(pet.getId(), pet.getType() == null ? 0 : pet.getType().getId(), pet.getName(),
					pet.getBirthDate() == null ? "" : pet.getBirthDate().toString());
		}
	}

}
