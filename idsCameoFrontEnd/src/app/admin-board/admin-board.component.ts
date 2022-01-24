import { Component, OnInit } from '@angular/core';
import { Ombrellone } from '../interfaces/ombrellone';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-admin-board',
  templateUrl: './admin-board.component.html',
  styleUrls: ['./admin-board.component.css']
})
export class AdminBoardComponent implements OnInit {

  form: any = {
    ombrelloneRowNumber: null,
    ombrelloneColumnNumber: null,
    prezzo: null,
    dataInizio: null,
    dataFine: null,
  };

  isAdmin!: boolean;
  ombrelloneCreated!: boolean;

  constructor(private ombrelloneService: OmbrelloneService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      this.isAdmin = true;
    }
    else {
      this.router.navigate(['/home']);
    }
  }

  onAddOmbrellone() {

    const newOmbrellone: Ombrellone = {
      ombrelloneRowNumber: this.form.ombrelloneRowNumber,
      ombrelloneColumnNumber: this.form.ombrelloneColumnNumber,
      prezzo: this.form.prezzo,
      dataInizio: new Date(this.form.dataInizio),
      dataFine: new Date(this.form.dataFine)
    };

    const token = this.tokenStorage.getToken()!;

    this.ombrelloneService.addOmbrellone(newOmbrellone, token).subscribe(
      data => {
        this.ombrelloneCreated = true;
      }
    );
  }

}
