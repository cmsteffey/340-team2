package com.csc340team2.mvc.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public List<Account> getAllBy();
    public Optional<Account> findByEmail(String email);
}