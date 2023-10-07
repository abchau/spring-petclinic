package org.springframework.samples.petclinic.system.configurator;

import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.pet.application.PetType;
import org.springframework.stereotype.Component;

@Component
class PetTypeConverter implements Converter<String, PetType>
{
    @Override
    public PetType convert(String id) 
    {
        return PetType.of(Integer.parseInt(id));
    }
}