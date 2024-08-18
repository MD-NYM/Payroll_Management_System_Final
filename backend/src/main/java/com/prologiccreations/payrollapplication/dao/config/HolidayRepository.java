package com.prologiccreations.payrollapplication.dao.config;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prologiccreations.payrollapplication.dao.super_classes.CrudDao;
import com.prologiccreations.payrollapplication.model.config.Holidays;

@Repository
public interface HolidayRepository extends JpaRepository<Holidays, Long>, CrudDao<Holidays, Long> {
    
    List<Holidays> findByYearAndMonth(int year, int month);
}
