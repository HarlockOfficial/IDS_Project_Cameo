import { TestBed } from '@angular/core/testing';

import { PrenotazioniService } from './ombrellone.service';

describe('PrenotazioniService', () => {
  let service: PrenotazioniService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrenotazioniService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
