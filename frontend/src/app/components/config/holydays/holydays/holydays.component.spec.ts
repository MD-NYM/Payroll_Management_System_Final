import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolydaysComponent } from './holydays.component';

describe('HolydaysComponent', () => {
  let component: HolydaysComponent;
  let fixture: ComponentFixture<HolydaysComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HolydaysComponent]
    });
    fixture = TestBed.createComponent(HolydaysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
