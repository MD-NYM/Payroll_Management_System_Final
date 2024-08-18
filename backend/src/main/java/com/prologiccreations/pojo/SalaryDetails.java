package com.prologiccreations.pojo;

import com.prologiccreations.payrollapplication.model.config.Salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDetails {

    private Long employeeId;
    private String employeeName;
    private int totalWorkDays;
    private int totalHolidays;
    private double totalWorkingHours;
    private double totalOvertimeHours;
    private double totalSalary;
    private double deduction;
    private double netSalary;
    private long salaryId;
    private Salary salary;

}
