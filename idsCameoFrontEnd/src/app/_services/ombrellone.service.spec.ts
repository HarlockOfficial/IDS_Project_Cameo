import { TestBed } from '@angular/core/testing';

import { OmbrelloneService } from './ombrellone.service';

describe('OmbrelloneService', () => {
  let service: OmbrelloneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OmbrelloneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
