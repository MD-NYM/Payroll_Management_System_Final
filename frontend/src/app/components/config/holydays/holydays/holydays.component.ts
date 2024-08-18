import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HolidayService } from '../holiday.service';

@Component({
  selector: 'app-holydays',
  templateUrl: './holydays.component.html',
  styleUrls: ['./holydays.component.scss']
})
export class HolydaysComponent implements OnInit {
  calendarForm: FormGroup;
  daysInMonth: (number | null)[]; // Include nulls for padding
  holidays: number[];
  monthNames: string[];

  constructor(private fb: FormBuilder, private holidayService: HolidayService) {
    this.calendarForm = this.fb.group({
      month: [new Date().getMonth()],
      year: [new Date().getFullYear()]
    });
    this.daysInMonth = [];
    this.holidays = [];
    this.monthNames = [
      'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ];
  }

  ngOnInit(): void {
    this.updateCalendarDays();
    this.calendarForm.valueChanges.subscribe(() => {
      this.updateCalendarDays();
    });
  }

  updateCalendarDays(): void {
    const { month, year } = this.calendarForm.value;
    const parsedMonth = Number(month);
    const parsedYear = Number(year);

    const firstDayOfMonth = new Date(parsedYear, parsedMonth, 1).getDay();
    const daysInMonth = this.getDaysInMonth(parsedMonth, parsedYear);

    const days = Array(firstDayOfMonth === 0 ? 6 : firstDayOfMonth - 1).fill(null);

    for (let i = 1; i <= daysInMonth; i++) {
      days.push(i);
    }

    this.daysInMonth = days;
  }

  getDaysInMonth(month: number, year: number): number {
    return new Date(year, month + 1, 0).getDate();
  }

  isHoliday(day: number | null): boolean {
    return day !== null && this.holidays.includes(day);
  }

  selectHoliday(day: number | null): void {
    if (day === null) return;
    if (this.isHoliday(day)) {
      this.holidays = this.holidays.filter(d => d !== day);
    } else {
      this.holidays.push(day);
    }
  }

  saveHolidaysToBackend(): void {
    const { month, year } = this.calendarForm.value;
    const parsedMonth = Number(month);
    const parsedYear = Number(year);
    const sortedHolidays = [...this.holidays].sort((a, b) => a - b);

    this.holidayService.saveHoliday(parsedYear, parsedMonth + 1, sortedHolidays.map(day => day.toString()))
      .subscribe(() => {
        console.log('Holidays saved successfully.');
      }, error => {
        console.error('Error saving holidays:', error);
      });
  }

  isCurrentMonth(day: number | null): boolean {
    return day !== null;
  }
}
