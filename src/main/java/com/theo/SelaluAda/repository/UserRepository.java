package com.theo.SelaluAda.repository;

import org.springframework.stereotype.Repository;
import com.theo.SelaluAda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
