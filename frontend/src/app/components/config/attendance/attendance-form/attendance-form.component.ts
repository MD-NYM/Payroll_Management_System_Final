import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { OperationStatus } from 'src/app/constants/status.enum';
import { Page } from 'src/app/dto/page.dto';
import { AppResponse } from 'src/app/dto/response.dto';
import { Attendance } from 'src/app/model/config/attendance-model';
import { Employee } from 'src/app/model/config/employee-model';
import { CrudService } from 'src/app/services/crud.service';
import { NotificationUtil } from 'src/app/utils/notification.util';
import { populateFormControl } from 'src/app/utils/object.util';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss']
})
export class AttendanceFormComponent implements OnInit {

  formGroup!: FormGroup;
  controls: any = {
    employee: new FormControl('', [Validators.required]),
    logDate: new FormControl('', [Validators.required]),
    present: new FormControl('', [Validators.required]),
    remarks: new FormControl('', []),
    hoursWorked: new FormControl('', [Validators.required]),
    overtime: new FormControl('', []),
    location: new FormControl('', [Validators.required]),
  };
  submitted = false;
  endPoint = "attendance";
  data: any = {};
  employees: Employee[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private service: CrudService,
    private noticeUtil: NotificationUtil
  ) { }

  ngOnInit() {
    this.createForm();
    this.data = this.service.data;
    if (this.data.id) {
      this.populateFormControl(this.formGroup.controls, this.data);
    }
    this.service.getList('employee', 0, 999999999).subscribe((res: AppResponse) => {
      const page: Page = res.data;
      this.employees = page.content;
    });
  }

  createForm() {
    this.formGroup = this.formBuilder.group(this.controls);
  }

  populateFormControl(controls: any, data: any) {
    for (const key in controls) {
      if (controls.hasOwnProperty(key) && data[key]) {
        controls[key].setValue(data[key]);
      }
    }
  }

  submitForm() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      return;
    }
    const values: Attendance = {
      ...this.data,
      ...this.formGroup.value,
      employee: { id: this.formGroup.value.employee }
    };
    this.service.save(values, this.endPoint).subscribe(response => {
      this.formGroup.reset();
      this.submitted = false;
      this.noticeUtil.showResponseMessage(response);
    },
      (error: Error) => {
        const res = { status: OperationStatus.FAILURE, message: "Server error! Please contact the support team." };
        this.noticeUtil.showResponseMessage(res);
        console.log(this.endPoint, error);
      });
  }
}
