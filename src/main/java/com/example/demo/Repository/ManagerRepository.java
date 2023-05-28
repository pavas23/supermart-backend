package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.Manager;
@Repository
public interface ManagerRepository extends JpaRepository<Manager,Integer> {
}
