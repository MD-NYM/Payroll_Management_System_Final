import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Attendance } from './model/Attendance';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AttendenceformService {
  private apiUrl = 'http://localhost:8080/attendance'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  checkIn(attendance: Attendance): Observable<Attendance> {
    return this.http.post<Attendance>(`${this.apiUrl}/checkin`, attendance);
  }

  checkOut(attendance: Attendance): Observable<Attendance> {
    return this.http.post<Attendance>(`${this.apiUrl}/checkout`, attendance);
  }

  getAllAttendances(): Observable<Attendance[]> {
    return this.http.get<Attendance[]>(`${this.apiUrl}/list`);
  }
}
