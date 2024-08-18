import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { OperationStatus } from 'src/app/constants/status.enum';
import { Page } from 'src/app/dto/page.dto';
import { AppResponse } from 'src/app/dto/response.dto';
import { Attendance } from 'src/app/model/config/attendance-model';
import { Employee } from 'src/app/model/config/employee-model';
import { PayrollTransaction } from 'src/app/model/config/payroll-transaction-model';
import { Salary } from 'src/app/model/config/salary-model';
import { CrudService } from 'src/app/services/crud.service';
import { NotificationUtil } from 'src/app/utils/notification.util';
import { SalaryDetails } from '../model/salary-details.model';
// import { populateFormControl } from 'src/app/utils/object.util';

@Component({
  selector: 'app-payroll-transaction-form',
  templateUrl: './payroll-transaction-form.component.html',
  styleUrls: ['./payroll-transaction-form.component.scss']
})
export class PayrollTransactionFormComponent implements OnInit {

  submitted = false;
  endPoint = "payrolltransaction";
  salaryDetails: SalaryDetails[] = [];
  employee = 0;
  payrollPeriod: string = `${new Date().getFullYear()}-${new Date().getMonth() + 1}`;
  constructor(private service: CrudService, private noticeUtil: NotificationUtil) { }

  ngOnInit() {
    // this.fetchSalaryDetails();
  }

  fetchSalaryDetails() {
    const [year, month] = this.payrollPeriod.split('-').map(Number);
    this.service.calculateSalary(year, month).subscribe((response: any) => {
      this.salaryDetails = response;
    });
  }
  disburse(salary: SalaryDetails) {
    const data = {
      employee: { id: salary.employeeId },
      payrollPeriod: this.payrollPeriod,
      salary: salary.salary,
      hoursWorked: salary.totalWorkingHours,
      overtimeHours: salary.totalOvertimeHours,
      grossEarnings: salary.totalSalary,
      deductions: salary.salary.deduction,
      netPay: salary.netSalary
    };

    this.service.save(data, this.endPoint).subscribe({
      next: (response) => {
        this.submitted = false;
        this.noticeUtil.showResponseMessage(response);
      },
      error: (error: Error) => {
        const res = { status: OperationStatus.FAILURE, message: "Server error! Please contact support team." };
        this.noticeUtil.showResponseMessage(res);
        console.log(this.endPoint, error);
      }
    });
  }
}