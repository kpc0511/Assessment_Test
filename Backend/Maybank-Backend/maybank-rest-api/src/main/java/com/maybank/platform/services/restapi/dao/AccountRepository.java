package com.maybank.platform.services.restapi.dao;

import com.maybank.platform.services.restapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.accountNumber IN :accountNumbers")
    List<Account> findByAccountNumbers(@Param("accountNumbers") Set<String> accountNumbers);
}
