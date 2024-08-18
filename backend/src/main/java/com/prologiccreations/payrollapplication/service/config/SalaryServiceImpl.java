package com.prologiccreations.payrollapplication.service.config;

import com.prologiccreations.payrollapplication.dao.config.AttendanceRepository;
import com.prologiccreations.payrollapplication.dao.config.EmployeeRepository;
import com.prologiccreations.payrollapplication.dao.config.HolidayRepository;
import com.prologiccreations.payrollapplication.dao.config.SalaryRepository;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.model.config.Employee;
import com.prologiccreations.payrollapplication.model.config.Holidays;
import com.prologiccreations.payrollapplication.model.config.Salary;
import com.prologiccreations.payrollapplication.service.super_classes.config.SalaryService;
import com.prologiccreations.pojo.SalaryDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.prologiccreations.payrollapplication.PayrollApplication.getCurrentUsername;
import static com.prologiccreations.payrollapplication.constants.enums.OperationStatus.FAILURE;
import static com.prologiccreations.payrollapplication.constants.enums.OperationStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository repository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final HolidayRepository holidaysRepository;
    private final SalaryRepository salaryRepository;

    @Override
    public Response storeData(Salary data) {
        String validationMsg = validate(data);
        if (validationMsg == null) {
            repository.save(data);
            return new Response(SUCCESS, "Successfully stored data", null);
        } else {
            return new Response(FAILURE, validationMsg, null);
        }
    }

    @Override
    public Response<Page<Salary>> getAll(Pageable pageable) {
        Page<Salary> page = repository.findByActive(true, pageable);
        return new Response<>(SUCCESS, null, page);
    }

    @Override
    public Response<Salary> getById(Long id) {
        Salary salary = repository.findById(id).orElse(new Salary());
        return new Response<>(SUCCESS, null, salary);
    }

    @Override
    public Response delete(Long id) {
        repository.softDeleteById(id, getCurrentUsername(), LocalDateTime.now());
        return new Response(SUCCESS, "Deleted successfully", null);
    }

    @Override
    public String validate(Salary data) {
        return checkDuplicate(data);
    }

    @Override
    public String checkDuplicate(Salary data) {
        // boolean salarynameExists;
        // if (data.hasId()) {
        // salarynameExists = repository.existsBySalaryname(data.getSalaryname(),
        // data.getId());
        // } else {
        // salarynameExists = repository.existsBySalaryname(data.getSalaryname());
        // }
        // return salarynameExists ? "Failed to register. Salary already exists" : null;
        return null;
    }

    @Override
    public List<SalaryDetails> calculateSalary(int year, int month) {
        LocalDate today = LocalDate.of(year, month, 1);
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        List<Integer> holidays = holidaysRepository.findByYearAndMonth(today.getYear(), today.getMonthValue())
                .stream().findFirst().orElse(new Holidays())
                .getDates().stream().map(Integer::parseInt).collect(Collectors.toList());
        List<SalaryDetails> salaryDetailsList = new LinkedList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            List<Attendance> attendanceList = attendanceRepository.findByEmployeeAndLogDateBetween(employee,
                    startOfMonth,
                    endOfMonth);
            Salary salary = salaryRepository.findByEmployeeAndActiveTrue(employee).stream().findFirst()
                    .orElse(new Salary());

            double hourlyRate = (salary.getSalaryAmount() / 30) / 8;
            double overTimeRate = hourlyRate * 2;

            int totalWorkDays = attendanceList.size();
            double totalWorkingHours = 0;
            double totalOvertimeHours = 0;
            Long lastWorkingDay = attendanceList.isEmpty() ? 0L
                    : attendanceList.get(attendanceList.size() - 1).getLogDate().getDayOfMonth();
            int totalHolidays = (int) holidays.stream().filter(holiday -> holiday <= lastWorkingDay).count();

            for (Attendance attendance : attendanceList) {
                Duration duration = Duration.between(attendance.getCheckinTime(), attendance.getCheckoutTime());
                Long totalHour = duration.toHours();
                Long workingHour = Math.min(totalHour, 8);
                Long overtime = totalHour - workingHour;

                totalWorkingHours += workingHour;
                totalOvertimeHours += overtime;
            }

            double grossSalary = (totalWorkingHours * hourlyRate) + (totalOvertimeHours * overTimeRate)
                    + (totalHolidays * hourlyRate * 8) + salary.getBonuses();
            double netSalary = Math.max(0, grossSalary - salary.getDeductions());

            String employeeName = employee.getFirstName() + " " + employee.getLastName();
            SalaryDetails salaryDetails = new SalaryDetails();
            salaryDetails.setEmployeeId(employee.getId());
            salaryDetails.setEmployeeName(employeeName);
            salaryDetails.setTotalSalary(grossSalary);
            salaryDetails.setSalary(salary);
            salaryDetails.setDeduction(salary.getDeductions());
            salaryDetails.setNetSalary(netSalary);
            salaryDetails.setTotalWorkDays(totalWorkDays);
            salaryDetails.setTotalHolidays(totalHolidays);
            salaryDetails.setTotalWorkingHours(totalWorkingHours);
            salaryDetails.setTotalOvertimeHours(totalOvertimeHours);
            salaryDetails.setSalaryId(salary.getId());
            salaryDetailsList.add(salaryDetails);

        }
        return salaryDetailsList;
    }

}
