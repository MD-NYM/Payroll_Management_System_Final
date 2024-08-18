import { Component, OnInit } from '@angular/core';
import { AttendenceformService } from './attendenceform.service';
import { Attendance } from './model/Attendance';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-attendenceform',
  templateUrl: './attendenceform.component.html',
  styleUrls: ['./attendenceform.component.scss']
})
export class AttendenceformComponent implements OnInit {
  
  remarks: any;

  constructor(private attendanceService: AttendenceformService) {
  }

  ngOnInit(): void {
  }

  onCheckIn(): void {
    this.attendanceService.checkIn({ remarks: this.remarks }).subscribe(
      (response) => {
        console.log('Checked in successfully:', response);
        // Optionally, you can handle success feedback or navigate to another page
      },
      (error) => {
        console.error('Error checking in:', error);
        // Handle error messages or show user feedback
      }
    );
  }

  onCheckOut(): void {
    this.attendanceService.checkOut({ remarks: this.remarks }).subscribe(
      (response) => {
        console.log('Checked out successfully:', response);
        // Optionally, you can handle success feedback or navigate to another page
      })
  }

}
