package com.prologiccreations.payrollapplication.service.config;

import static com.prologiccreations.payrollapplication.PayrollApplication.getCurrentUsername;
import static com.prologiccreations.payrollapplication.constants.enums.OperationStatus.FAILURE;
import static com.prologiccreations.payrollapplication.constants.enums.OperationStatus.SUCCESS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prologiccreations.payrollapplication.dao.config.AttendanceRepository;
import com.prologiccreations.payrollapplication.dao.config.PayrollTransactionRepository;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Attendance;
import com.prologiccreations.payrollapplication.model.config.Employee;
import com.prologiccreations.payrollapplication.model.config.PayrollTransaction;
import com.prologiccreations.payrollapplication.service.super_classes.config.PayrollTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollTransactionServiceImpl implements PayrollTransactionService {

    private final PayrollTransactionRepository repository;
    private final AttendanceRepository attendanceRepository;

    // @Override
    // public Response storeData(PayrollTransaction data) {
    //     String validationMsg = validate(data);
    //     if (validationMsg == null) {
    //         repository.save(data);
    //         return new Response(SUCCESS, "Successfully stored data", null);
    //     } else {
    //         return new Response(FAILURE, validationMsg, null);
    //     }
    // }
    @Override
    public Response storeData(PayrollTransaction data) {
        String validationMsg = validate(data);
        if (validationMsg == null) {
            // Calculate total hours worked
            double totalHoursWorked = calculateTotalHoursWorked(data.getEmployee(), data.getPayrollPeriod());
            data.setHoursWorked(totalHoursWorked);
            repository.save(data);
            return new Response(SUCCESS, "Successfully stored data", null);
        } else {
            return new Response(FAILURE, validationMsg, null);
        }
    }

    @Override
    public Response<Page<PayrollTransaction>> getAll(Pageable pageable) {
        Page<PayrollTransaction> page = repository.findByActive(true, pageable);
        return new Response<>(SUCCESS, null, page);
    }

    @Override
    public Response<PayrollTransaction> getById(Long id) {
        PayrollTransaction payrollTransaction = repository.findById(id).orElse(new PayrollTransaction());
        return new Response<>(SUCCESS, null, payrollTransaction);
    }

    @Override
    public Response delete(Long id) {
        repository.softDeleteById(id, getCurrentUsername(), LocalDateTime.now());
        return new Response(SUCCESS, "Deleted successfully", null);
    }

    @Override
    public String validate(PayrollTransaction data) {
        return checkDuplicate(data);
    }

    @Override
    public String checkDuplicate(PayrollTransaction data) {
//        boolean payrollTransactionnameExists;
//        if (data.hasId()) {
//            payrollTransactionnameExists = repository.existsByPayrollTransactionname(data.getPayrollTransactionname(), data.getId());
//        } else {
//            payrollTransactionnameExists = repository.existsByPayrollTransactionname(data.getPayrollTransactionname());
//        }
//        return payrollTransactionnameExists ? "Failed to register. PayrollTransaction already exists" : null;
        return null;
    }

    private double calculateTotalHoursWorked(Employee employee, String payrollPeriod) {
        // Parse payrollPeriod to get start and end dates
        LocalDate startDate = LocalDate.parse(payrollPeriod + "-01");
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Attendance> attendances = attendanceRepository.findByEmployeeAndLogDateBetween(employee, startDate, endDate);
        return attendances.stream().mapToInt(Attendance::calculateHoursWorked).sum();
    }

}
