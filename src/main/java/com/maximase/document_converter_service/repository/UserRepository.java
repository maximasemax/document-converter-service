package com.maximase.document_converter_service.repository;

import com.maximase.document_converter_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface  UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
