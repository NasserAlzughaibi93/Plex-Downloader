import { TestBed } from '@angular/core/testing';

import { ComponentMessagingService } from './component-messaging.service';

describe('ComponentMessagingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ComponentMessagingService = TestBed.get(ComponentMessagingService);
    expect(service).toBeTruthy();
  });
});
