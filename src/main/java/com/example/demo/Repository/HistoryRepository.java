package com.example.demo.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Model.History;

public interface HistoryRepository extends JpaRepository<History,Integer> {
        @Query(value ="SELECT * FROM History where CustomerID = :CustomerId", nativeQuery= true)
        public List<History> getByCustomerID(@Param("CustomerId") int id);
        @Query(value ="SELECT * FROM History where ProductID = :ProductId", nativeQuery= true)
        public List<History> getByProductID(@Param("ProductId") int id);
        @Query(value = "SELECT*FROM History where MONTH(date_time) = :month", nativeQuery = true)
        public List<History> getMonthlyHistory(@Param("month") int month);
}
