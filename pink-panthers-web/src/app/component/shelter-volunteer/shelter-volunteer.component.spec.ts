import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShelterVolunteerComponent } from './shelter-volunteer.component';

describe('ShelterVolunteerComponent', () => {
  let component: ShelterVolunteerComponent;
  let fixture: ComponentFixture<ShelterVolunteerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShelterVolunteerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShelterVolunteerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
