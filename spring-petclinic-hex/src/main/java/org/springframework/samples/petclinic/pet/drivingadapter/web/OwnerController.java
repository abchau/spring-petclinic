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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.samples.petclinic.pet.application.drivingport.CreateOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.ShowOwnerPort;
import org.springframework.samples.petclinic.pet.application.drivingport.UpdateOwnerPort;
import org.springframework.samples.petclinic.pet.domain.Owner;
import org.springframework.samples.petclinic.pet.domain.Owner.PaginatedOwner;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author github.com/abchau
 */
@Controller
/*final*/ class OwnerController {

	private final ShowOwnerPort showOwnerPort;

	private final CreateOwnerPort createOwnerPort;

	private final UpdateOwnerPort updateOwnerPort;

	@Autowired
	public OwnerController(ShowOwnerPort showOwnerPort, CreateOwnerPort createOwnerPort,
			UpdateOwnerPort updateOwnerPort) {
		this.showOwnerPort = showOwnerPort;
		this.createOwnerPort = createOwnerPort;
		this.updateOwnerPort = updateOwnerPort;
	}

	@GetMapping("/owners/new")
	public String initCreationForm(ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", new Owner());
		}

		model.put("mode", "new");
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@ModelAttribute("form") @Valid Owner form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			
			return "redirect:/owners/new";
		}

		Owner createdOwner = createOwnerPort.createOwner(form);

		return "redirect:/owners/" + createdOwner.getId();
	}

	@GetMapping("/owners/find")
	public String initFindForm(@ModelAttribute("form") Owner form, BindingResult result) {
		if (form.getLastName() == null) {
			result.rejectValue("lastName", "notFound", "not found");
		}

		return "owners/findOwners";
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, @ModelAttribute("form") Owner form,
			BindingResult result, Model model) {
		// allow parameterless GET request for /owners to return all records
		if (form.getLastName() == null) {
			form.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		PaginatedOwner ownersResults = showOwnerPort.findPaginatedForOwnersLastName(form.getLastName(), page);
		if (ownersResults.isEmpty()) {
			// no owners found
			return "redirect:/owners/find?lastName=" + form.getLastName();
		}

		if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			Owner owner = ownersResults.getContent().get(0);
			return "redirect:/owners/" + owner.getId();
		}

		// multiple owners found
		model.addAttribute("form", form);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ownersResults.getTotalPages());
		model.addAttribute("totalItems", ownersResults.getTotalElements());
		model.addAttribute("listOwners", ownersResults.getContent());

		return "owners/ownersList";
	}

	@GetMapping("/owners/{id}/edit")
	public String initUpdateOwnerForm(@PathVariable("id") Integer id, ModelMap model) {
		if (model.get("form") == null) {
			model.put("form", showOwnerPort.findById(id));
		}
		model.put("mode", "edit");
		
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/owners/{id}/edit")
	public String processUpdateOwnerForm(@Valid Owner form, BindingResult result, RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer id) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
			
			return "redirect:/owners/{id}/edit";
		}

		// demostrates web layer validation
		if (form.getId() != id) {
			redirectAttributes.addFlashAttribute("form", form);
			FieldError fieldError = new FieldError(Owner.class.getName(), "id", "gocha!");
			redirectAttributes.addFlashAttribute("errors", List.of(fieldError));
			
			return "redirect:/owners/{id}/edit";
		}

		Owner updatedOwner = updateOwnerPort.updateOwner(form);

		return "redirect:/owners/{id}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{id}")
	public ModelAndView showOwner(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = showOwnerPort.findWithPetsById(id);
		mav.addObject(owner);
		
		return mav;
	}

}
