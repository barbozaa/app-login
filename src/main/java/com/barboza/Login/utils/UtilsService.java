package com.barboza.Login.utils;

import com.barboza.Login.models.User;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtilsService {
    public MappingJacksonValue mapDTO(User user) {
        log.info(">> mapDTO");
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        log.info("<< mapDTO");
        return mappingJacksonValue;
    }
}
