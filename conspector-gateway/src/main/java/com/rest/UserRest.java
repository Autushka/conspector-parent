package com.rest;

import com.dto.UserPasswordRequestDto;
import com.dto.UserRequestDto;
import com.dto.UserResponseDto;
import com.repository.IUserRepository;
import com.entity.User;


import com.repository.exception.NotAuthorizedActionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

/**
 * Created by aautushk on 8/30/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class UserRest {
    @Autowired
	private IUserRepository UserRepository;

    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    private UserResponseDto convertToDto(User user) {
        UserResponseDto userDro = modelMapper.map(user, UserResponseDto.class);
        return userDro;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);

        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");
        String encodedPassword = standardPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);//TODO: initially should be false (email validation step is missing for now)

        UserRepository.save(user);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        return userResponseDto;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    // example of multicolumn sorting with pagination:
    // http://localhost:8080/gateway/getUsers?pag=0&size=1&sort=username,desc&sort=enabled,asc
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        List<UserResponseDto> usersResponseDto = new ArrayList<UserResponseDto>();
        Page<User> users = UserRepository.findAll(pageable);

        for(User user : users){
            UserResponseDto userResponseDto = this.convertToDto(user);
            usersResponseDto.add(userResponseDto);
        }

        return new PageImpl<>(usersResponseDto, pageable, users.getTotalElements());
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public void changePassword(@RequestBody UserPasswordRequestDto userPasswordRequestDto, Principal user) {
        String username =  user.getName();

        User userFromDB = UserRepository.findByUsername(username);

        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");

        if(!standardPasswordEncoder.matches(userPasswordRequestDto.getCurrentPassword(), userFromDB.getPassword())){
            throw new NotAuthorizedActionException("Not authorized action detected...");
        }

        String encodedNewPassword = standardPasswordEncoder.encode(userPasswordRequestDto.getNewPassword());
        userFromDB.setPassword(encodedNewPassword);

        UserRepository.save(userFromDB);
    }
}
