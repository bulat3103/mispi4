package com.example.Web4.repositories;

import com.example.Web4.entities.Point;
import com.example.Web4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
