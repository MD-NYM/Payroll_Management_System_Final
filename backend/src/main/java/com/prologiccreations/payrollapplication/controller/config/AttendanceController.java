package com.prologiccreations.payrollapplication.controller.config;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prologiccreations.payrollapplication.controller.super_classes.CrudController;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.service.super_classes.config.AttendanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController implements CrudController<Attendance, Long> {
    
    private final AttendanceService attendanceService;


    @PostMapping("/checkin")
    public ResponseEntity<Attendance> checkIn(@RequestBody Attendance attendance) {
        Attendance savedAttendance = attendanceService.checkIn(attendance);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Attendance> checkOut(@RequestBody Attendance attendance) {
        Attendance savedattendance = attendanceService.checkOut(attendance);
        return new ResponseEntity<>(savedattendance, HttpStatus.CREATED);
    }
        
    @GetMapping("/list")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> storeData(Attendance data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'storeData'");
    }

    @Override
    public ResponseEntity<Response<Page<Attendance>>> getAll(Integer pageNumber, Integer pageSize, String sortDirection,
            String sortColumns) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public ResponseEntity<Response<Attendance>> getOne(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public ResponseEntity<Response> delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    // @Override
    // public ResponseEntity<Response> storeData(Attendance data) {
    //     Response response = attendanceService.storeData(data);
    //     return ResponseEntity.ok(response);
    // }

    // @Override
    // public ResponseEntity<Response<Page<Attendance>>> getAll(Integer pageNumber, Integer pageSize, String sortDirection, String sortColumns) {
    //     Pageable pageable = PageUtil.getPageable(pageNumber, pageSize, sortDirection, sortColumns);
    //     Response<Page<Attendance>> response = attendanceService.getAll(pageable);
    //     return ResponseEntity.ok(response);
    // }

    // @Override
    // public ResponseEntity<Response<Attendance>> getOne(Long id) {
    //     Response<Attendance> response = attendanceService.getById(id);
    //     return ResponseEntity.ok(response);
    // }

    // @Override
    // public ResponseEntity<Response> delete(Long id) {
    //     Response response = attendanceService.delete(id);
    //     return ResponseEntity.ok(response);
    // }
}
