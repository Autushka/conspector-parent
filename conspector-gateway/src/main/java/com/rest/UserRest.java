package com.rest;

import com.dto.UserDto;
import com.repository.IGatewayRepository;
import com.entity.User;


import javafx.concurrent.Worker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by aautushk on 8/30/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class UserRest {
    @Autowired
	private IGatewayRepository GatewayRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List<UserDto> getUsers() {
        List<UserDto> usersDto = new ArrayList<UserDto>();

        List<User> users = GatewayRepository.findAll();

        for(User user : users){
            UserDto userDto = this.convertToDto(user);
            usersDto.add(userDto);
        }

        return usersDto;
    }

    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    private UserDto convertToDto(User user) {
        UserDto userDro = modelMapper.map(user, UserDto.class);
        return userDro;
    }
}
