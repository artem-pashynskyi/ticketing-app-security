package com.ticketingapp.converter;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProjectDTOConverter implements Converter<String, ProjectDTO> {

    @Autowired
    ProjectService projectService;

    @Override
    public ProjectDTO convert(String source) {
        return projectService.getByProjectCode(source);
    }

}
