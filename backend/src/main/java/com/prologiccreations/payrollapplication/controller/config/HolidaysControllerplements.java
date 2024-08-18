package com.prologiccreations.payrollapplication.controller.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prologiccreations.payrollapplication.model.config.Holidays;
import com.prologiccreations.payrollapplication.service.super_classes.config.HolidayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("holidays/")
public class HolidaysControllerplements {

    private final HolidayService holidayService;

    @PostMapping("/save")
    public ResponseEntity<Holidays> saveHoliday(@RequestBody Holidays holiday) {
        Holidays savedHoliday = holidayService.saveHoliday(holiday);
        return new ResponseEntity<>(savedHoliday, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Holidays>> getAllHolidays() {
        List<Holidays> holidays = holidayService.getAllHolidays();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Holidays> getHolidayById(@PathVariable("id") Long id) {
        Holidays holiday = holidayService.getHolidayById(id);
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Holidays> updateHoliday(
            @PathVariable("id") Long id,
            @RequestBody Holidays holidayDetails) {
        Holidays updatedHoliday = holidayService.updateHoliday(id, holidayDetails);
        return new ResponseEntity<>(updatedHoliday, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable("id") Long id) {
        holidayService.deleteHoliday(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Holidays>> findHolidaysByYearAndMonth(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<Holidays> holidays = holidayService.findHolidaysByYearAndMonth(year, month);
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }
}
