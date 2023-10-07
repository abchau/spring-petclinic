package org.springframework.samples.petclinic.system.configurator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	ApplicationContext ctx;

	@Override
	public void addFormatters (FormatterRegistry registry)
	{
		registry.addConverter((PetTypeConverter)ctx.getBean("petTypeConverter"));
	}
}
