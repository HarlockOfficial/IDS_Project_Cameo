import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { PrenotazioniService as OmbrelloneService } from '../_services/ombrellone.service';
@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrls: ['./prenota.component.css']
})
export class PrenotaComponent implements OnInit {

  errorMessage = '';

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private ombrelloneService: OmbrelloneService) { }

  ngOnInit(): void {
    this.onGetOmbrelloni();
  }

  onGetOmbrelloni(): void {
    this.ombrelloneService.allOmbrelloni()?.subscribe(
      data => {
        console.log(data);
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }
}
