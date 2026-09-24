package com.campus.cafeteria.repository;
import com.campus.cafeteria.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MenuRepository extends JpaRepository<MenuItem,Long>{}
