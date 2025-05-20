package com.theo.SelaluAda.repository;

import com.theo.SelaluAda.enums.ProvinceToBranch;
import com.theo.SelaluAda.model.Branch;
import com.theo.SelaluAda.model.User;
import com.theo.SelaluAda.model.UserCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<UserCustomer, UUID> {
    Optional<UserCustomer> findByUser(User user);
}