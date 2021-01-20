package com.ticketingapp.converter;

import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDTOConverter implements Converter<String, UserDTO> {

    @Autowired
    UserService userService;

    @Override
    public UserDTO convert(String source) {
        return userService.findByUserName(source);
    }
}
