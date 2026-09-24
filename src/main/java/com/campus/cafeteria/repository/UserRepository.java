package com.campus.cafeteria.repository;
import com.campus.cafeteria.model.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<CafeUser,Long>{Optional<CafeUser> findByEmailIgnoreCase(String email);}
