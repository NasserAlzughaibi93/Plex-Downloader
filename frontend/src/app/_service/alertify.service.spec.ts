import { TestBed, inject } from '@angular/core/testing';

import { AlertifyService } from './alertify.service';

describe('AlertifyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AlertifyService]
    });
  });

  it('should be created', inject([AlertifyService], (service: AlertifyService) => {
    expect(service).toBeTruthy();
  }));
});
