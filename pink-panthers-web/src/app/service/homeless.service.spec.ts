import { TestBed, inject } from '@angular/core/testing';

import { HomelessService } from './homeless.service';

describe('HomelessService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HomelessService]
    });
  });

  it('should be created', inject([HomelessService], (service: HomelessService) => {
    expect(service).toBeTruthy();
  }));
});
