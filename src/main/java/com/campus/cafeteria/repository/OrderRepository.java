package com.campus.cafeteria.repository;
import com.campus.cafeteria.model.CafeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
public interface OrderRepository extends JpaRepository<CafeOrder,Long>{@EntityGraph(attributePaths={"items","items.menuItem"}) List<CafeOrder> findByUserIdOrderByCreatedAtDesc(Long userId);}
