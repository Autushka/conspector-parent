package com.rest

import com.dto.UserRequestDto
import com.dto.UserResponseDto
import com.entity.UserEntity
import com.repository.IUserRepository
import org.springframework.data.domain.PageImpl
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.awt.print.Pageable

/**
 * Created by aautushk on 9/9/2015.
 */
class UserRestTest extends Specification {
    UserRest userRest = new UserRest();

    def "Create user end point"() {
        setup:
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setUsername("test");
            userRequestDto.setFirstName("testFirstName");
            userRequestDto.setLastName("testLastName");
            userRequestDto.setPassword("password");

            def mockUserRepository = Mock(IUserRepository.class)
            userRest.@userRepository = mockUserRepository
        when:
            ResponseEntity<UserResponseDto> userResponseDto = userRest.createUser(userRequestDto);
        then:
            1 * mockUserRepository.save(_)
    }

    def "Get users end point"() {
        setup:
            Pageable pageable;

        def mockUserRepository = Mock(IUserRepository.class)
            userRest.@userRepository = mockUserRepository
        when:
            PageImpl<UserEntity> response = userRest.getUsers(pageable);
        then:
            1 * mockUserRepository.findAll(pageable)
    }
}
