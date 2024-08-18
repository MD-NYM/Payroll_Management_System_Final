import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HolidayService {
  private apiUrl = `${environment.apiUrl}/holidays`; // Update with your Spring Boot API URL

  constructor(private http: HttpClient) { }

  saveHoliday(year: number, month: number, dates: string[]): Observable<any> {
    const holidayData = { year, month, dates };
    return this.http.post<any>(`${this.apiUrl}/save`, holidayData);
  }
}
