package com.prologiccreations.payrollapplication.service.super_classes.config;

import java.util.List;

import com.prologiccreations.payrollapplication.model.config.Holidays;
import com.prologiccreations.payrollapplication.service.super_classes.CrudService;

public interface HolidayService extends CrudService<Holidays, Long> {
    Holidays saveHoliday(Holidays holiday);

    List<Holidays> getAllHolidays();

    Holidays getHolidayById(Long id);

    Holidays updateHoliday(Long id, Holidays holidayDetails);

    void deleteHoliday(Long id);

    List<Holidays> findHolidaysByYearAndMonth(int year, int month);
}
