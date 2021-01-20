package com.ticketingapp.mapper;

import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    public TaskDTO convertToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
