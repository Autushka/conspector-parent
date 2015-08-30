package com.repository;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 8/30/2015.
 */

@Repository
public interface IGatewayRepository extends JpaRepository<User, Long> {
    List<User> findAll();
}
