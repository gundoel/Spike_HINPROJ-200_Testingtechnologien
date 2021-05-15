package com.swisscom.uamspiketesting.repository;

import com.swisscom.uamspiketesting.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// extend JpaRepository if batching or paging is required
public interface UserRepository extends CrudRepository<User, Integer> {
   List<User> findByEmail(String email);
   @Query("" +
           "SELECT CASE WHEN COUNT(u) > 0 THEN " +
           "TRUE ELSE FALSE END " +
           "FROM uam_tst_user u " +
           "WHERE u.email = ?1"
   )
   boolean doesUserWithEmailExist(String email);
}
