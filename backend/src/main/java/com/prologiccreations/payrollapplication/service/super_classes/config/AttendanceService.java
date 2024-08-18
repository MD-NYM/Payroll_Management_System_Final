package com.prologiccreations.payrollapplication.service.super_classes.config;

import java.util.List;

import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.service.super_classes.CrudService;

public interface AttendanceService extends CrudService<Attendance, Long> {

    // Response storeData(Attendance data);
    // Response<Page<Attendance>> getAll(Pageable pageable);
    // Response<Attendance> getById(Long id);
    // Response delete(Long id);
    // String validate(Attendance data);
    // String checkDuplicate(Attendance data);

    Attendance saveAttendance(Attendance attendance);

    Attendance findById(Long id);

    List<Attendance> getAllAttendances();

    Attendance checkIn(Attendance attendance);

    Attendance checkOut(Attendance attendance);
}
