@org.springframework.modulith.ApplicationModule(
	allowedDependencies = {
		"pet::api", "vet::api"
	}
  )
package org.springframework.samples.petclinic.system;