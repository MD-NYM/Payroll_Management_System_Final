import { TestBed } from '@angular/core/testing';

import { AttendenceformService } from './attendenceform.service';

describe('AttendenceformService', () => {
  let service: AttendenceformService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttendenceformService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
