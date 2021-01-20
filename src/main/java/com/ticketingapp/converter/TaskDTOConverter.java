package com.ticketingapp.converter;

import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class TaskDTOConverter implements Converter<String, TaskDTO> {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskDTO convert(String source) {
        Long id = Long.parseLong(source);
        return taskService.findById(id);
    }
}
