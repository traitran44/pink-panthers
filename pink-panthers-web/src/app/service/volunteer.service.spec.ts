import { TestBed, inject } from '@angular/core/testing';

import { VolunteerService } from './volunteer.service';

describe('VolunteerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VolunteerService]
    });
  });

  it('should be created', inject([VolunteerService], (service: VolunteerService) => {
    expect(service).toBeTruthy();
  }));
});
