package com.prologiccreations.payrollapplication.service.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prologiccreations.payrollapplication.dao.config.AttendanceRepository;
import com.prologiccreations.payrollapplication.dao.config.EmployeeRepository;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.model.config.Employee;
import com.prologiccreations.payrollapplication.service.super_classes.config.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance findById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // @Override
    // public Attendance checkIn(Attendance attendance) {
    // String username =
    // SecurityContextHolder.getContext().getAuthentication().getName();
    // Employee employee = employeeRepository.findByUsername(username).orElse(null);
    // attendance.setEmployee(employee);
    // attendance.setLogDate(LocalDate.now());
    // attendance.setCheckinTime(LocalDateTime.now());
    // attendance.setPresent(true); // Assuming the employee is present at check-in
    // return attendanceRepository.save(attendance);
    // }

    // @Override
    // public Attendance checkOut(Attendance attendance) {
    // String username =
    // SecurityContextHolder.getContext().getAuthentication().getName();
    // Attendance savedAttendance =
    // attendanceRepository.findByLogDateAndUsername(LocalDate.now(),
    // username).orElse(new Attendance());
    // savedAttendance.setRemarks(attendance.getRemarks());
    // savedAttendance.setCheckoutTime(LocalDateTime.now());
    // savedAttendance.setPresent(false); // Assuming the employee is absent at
    // check-out
    // return attendanceRepository.save(savedAttendance);
    // }

    @Override
    public Attendance checkIn(Attendance attendance) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        Attendance existingAttendance = attendanceRepository.findByLogDateAndEmployee(LocalDate.now(), employee)
                .orElse(new Attendance());
        if (existingAttendance.getCheckinTime() != null) {
            return existingAttendance;
        }
        existingAttendance.setCheckinTime(LocalDateTime.now());
        existingAttendance.setLogDate(LocalDate.now());
        existingAttendance.setEmployee(employee);
        existingAttendance.setRemarks(attendance.getRemarks());
        existingAttendance.setPresent(false); // Assuming the employee is absent at check-out
        return attendanceRepository.save(existingAttendance);
    }

    @Override
    public Attendance checkOut(Attendance attendance) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeRepository.findByUsername(username).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        Attendance existingAttendance = attendanceRepository.findByLogDateAndEmployee(LocalDate.now(), employee)
                .orElseThrow(() -> new RuntimeException("Please check-in before checking out."));
        existingAttendance.setCheckoutTime(LocalDateTime.now());
        existingAttendance.setLogDate(LocalDate.now());
        existingAttendance.setEmployee(employee);
        existingAttendance.setRemarks(attendance.getRemarks());
        existingAttendance.setPresent(false); // Assuming the employee is absent at check-out
        return attendanceRepository.save(existingAttendance);
    }

    @Override
    @NonNull
    public Response storeData(@NonNull Attendance data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'storeData'");
    }

    @Override
    @NonNull
    public Response<Page<Attendance>> getAll(@NonNull Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    @NonNull
    public Response<Attendance> getById(@NonNull Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    @NonNull
    public Response delete(@NonNull Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    @Nullable
    public String validate(@NonNull Attendance data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    @Nullable
    public String checkDuplicate(@NonNull Attendance data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkDuplicate'");
    }
    // private final AttendanceRepository repository;

    // @Override
    // public Response<String> storeData(Attendance data) {
    // String validationMsg = validate(data);
    // if (validationMsg == null) {
    // repository.save(data);
    // return new Response<String>(SUCCESS, "Successfully stored data", null);
    // } else {
    // return new Response<String>(FAILURE, validationMsg, null);
    // }
    // }

    // @Override
    // public Response<Page<Attendance>> getAll(Pageable pageable) {
    // Page<Attendance> page = repository.findByActive(true, pageable);
    // return new Response<>(SUCCESS, null, page);
    // }

    // @Override
    // public Response<Attendance> getById(Long id) {
    // Attendance benefits = repository.findById(id).orElse(new Attendance());
    // return new Response<>(SUCCESS, null, benefits);
    // }

    // @Override
    // public Response<String> delete(Long id) {
    // repository.softDeleteById(id, getCurrentUsername(), LocalDateTime.now());
    // return new Response<String>(SUCCESS, "Deleted successfully", null);
    // }

    // @Override
    // public String validate(Attendance data) {
    // return checkDuplicate(data);
    // }

    // @Override
    // public String checkDuplicate(Attendance data) {
    // // boolean benefitsnameExists;
    // // if (data.hasId()) {
    // // benefitsnameExists =
    // repository.existsByAttendancename(data.getAttendancename(), data.getId());
    // // } else {
    // // benefitsnameExists =
    // repository.existsByAttendancename(data.getAttendancename());
    // // }
    // // return benefitsnameExists ? "Failed to register. Attendance already
    // exists" : null;
    // return null;
    // }

}
