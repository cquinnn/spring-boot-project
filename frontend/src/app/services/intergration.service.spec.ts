import { TestBed } from '@angular/core/testing';

import { IntergrationService } from './intergration.service';

describe('IntergrationService', () => {
  let service: IntergrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntergrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
