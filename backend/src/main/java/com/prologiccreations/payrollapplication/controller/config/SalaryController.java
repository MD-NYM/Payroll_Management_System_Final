package com.prologiccreations.payrollapplication.controller.config;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prologiccreations.payrollapplication.controller.super_classes.CrudController;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Salary;
import com.prologiccreations.payrollapplication.service.super_classes.config.SalaryService;
import com.prologiccreations.payrollapplication.utils.PageUtil;
import com.prologiccreations.pojo.SalaryDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("salary/")
public class SalaryController implements CrudController<Salary, Long> {
    private final SalaryService salaryService;

    @Override
    public ResponseEntity<Response> storeData(Salary data) {
        Response response = salaryService.storeData(data);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<Page<Salary>>> getAll(Integer pageNumber, Integer pageSize, String sortDirection,
            String sortColumns) {
        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize, sortDirection, sortColumns);
        Response<Page<Salary>> response = salaryService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<Salary>> getOne(Long id) {
        Response<Salary> response = salaryService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> delete(Long id) {
        Response response = salaryService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculate-salary/{year}/{month}")
    public ResponseEntity<List<SalaryDetails>> calculateSalary(
            @PathVariable("year") int year,
            @PathVariable("month") int month) {
        List<SalaryDetails> salaryDetails = salaryService.calculateSalary(year, month);
        return ResponseEntity.ok(salaryDetails);
    }

}
