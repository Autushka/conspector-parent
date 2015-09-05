//package com.tasconline.filemanager.rest
//
//import com.repository.IUserRepository
//import org.mockito.Mockito
//import org.springframework.data.domain.PageImpl
//import org.springframework.data.domain.PageRequest
//import spock.lang.Specification
//
//public class UserRestUnitSpec extends Specification {
//
//    def setup() {
//        userRest = new UserRest()
//    }
//
//    def "Get user set should succeed"() {
//        setup:
//
//        def mockUserRepository = Mock(IUserRepository.class)
//        userRest.@UserRepository = mockUserRepository
//
//
//        when:
//            userRest.getUsers(_);
//        then:
//            1 * mockUserRepository.getUsers(_)
//    }
//}
