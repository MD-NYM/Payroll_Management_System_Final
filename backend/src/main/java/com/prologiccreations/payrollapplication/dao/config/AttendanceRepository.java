package com.prologiccreations.payrollapplication.dao.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prologiccreations.payrollapplication.dao.super_classes.CrudDao;
import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.model.config.Employee;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, CrudDao<Attendance, Long> {

    
    @Query("SELECT a FROM Attendance a JOIN a.employee e WHERE a.logDate = ?1 AND e.username = ?2")
    Optional<Attendance> findByLogDateAndUsername(LocalDate logDate, String username);

    Optional<Attendance> findByLogDateAndEmployee(LocalDate logDate, Employee employee);

    List<Attendance> findByEmployeeAndLogDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    // @Modifying
    // @Query(value = "UPDATE Attendance e " +
    // "SET e.active = false, e.modifiedBy = :modifiedBy, e.modifiedDate =
    // :modifiedDate " +
    // "where e.id = :id")
    // int softDeleteById(@Param("id") Long id, @Param("modifiedBy") String
    // modifiedBy, @Param("modifiedDate") LocalDateTime modifiedDate);
}