import { Component, OnInit } from '@angular/core';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { Ombrellone } from '../interfaces/ombrellone';
import { ShoppingCartService } from '../_services/shopping-cart.service';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';

@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrls: ['./prenota.component.css']
})
export class PrenotaComponent implements OnInit {

  errorMessage = '';
  listaOmbrelloni!: Ombrellone[];
  listaOrdine: PrenotazioneSpiaggia[] = []
  isOrder: boolean = false;
  startDate!: Date;
  endDate!: Date;
  isDate: boolean = false;

  formDate: any = {
    startDate: null,
    endDate: null,
  };

  constructor(private ombrelloneService: OmbrelloneService, private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
  }

  onGetOmbrelloni(): void {
    this.ombrelloneService.allFreeOmbrelloni(this.startDate, this.endDate)?.subscribe(
      data => {
        console.log(data);
        this.listaOmbrelloni = data;
        console.log(this.listaOmbrelloni);
      },
      err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    );
  }

  //Rimuove un ombrellone dalla lista degli ombrelloni prenotabili
  removeFromListaOmbrelloni(ombrellone: Ombrellone) {
    this.listaOmbrelloni.forEach((element, index) => {
      if (element == ombrellone) this.listaOmbrelloni.splice(index, 1);
    });
  }

  addToOrder(ombrellone: Ombrellone) {
    const newPrenotazioneSpiaggia: PrenotazioneSpiaggia = {
      lettini: 1,
      sdraio: 1,
      startDate: new Date(1 / 11 / 1111),
      endDate: new Date(1 / 11 / 1111),
      ombrellone: ombrellone,
    };
    if (this.listaOrdine.length == 0) {
      this.removeFromListaOmbrelloni(ombrellone);
      this.isOrder = true;
    }
    else {
      this.listaOrdine.forEach((element) => {
        console.log(element.ombrellone, ombrellone)
        if (element.ombrellone == ombrellone) {
          return;
        }
        else {
          this.removeFromListaOmbrelloni(ombrellone);
          this.isOrder = true;
        }
      });
    }
    this.listaOrdine.push(newPrenotazioneSpiaggia);
  }

  removeFromOrder(prenotazioneSpiaggia: PrenotazioneSpiaggia) {
    this.listaOrdine.forEach((element, index) => {
      if (element.ombrellone == prenotazioneSpiaggia.ombrellone) {
        this.listaOrdine.splice(index, 1);
        this.listaOmbrelloni.push(prenotazioneSpiaggia.ombrellone);
      }
    });
    if (this.listaOrdine.length == 0) {
      this.isOrder = false;
    }
  }

  addToCart() {
    this.listaOrdine.forEach(element => {
      this.shoppingCartService.addItem(element);
    });
    this.listaOrdine = [];
    this.isOrder = false;
  }

  addOmbrelloneGadget(prenotazioneSpiaggia: PrenotazioneSpiaggia, elemento: string) {
    if (elemento == 'l') {
      prenotazioneSpiaggia.lettini = prenotazioneSpiaggia.lettini + 1;
    }
    else if (elemento == 's') {
      prenotazioneSpiaggia.sdraio = prenotazioneSpiaggia.sdraio + 1;
    }
  }

  removeOmbrelloneGadget(prenotazioneSpiaggia: PrenotazioneSpiaggia, elemento: string) {
    if (elemento == 'l' && prenotazioneSpiaggia.lettini > 1) {
      prenotazioneSpiaggia.lettini = prenotazioneSpiaggia.lettini - 1;
    }
    else if (elemento == 's' && prenotazioneSpiaggia.sdraio > 1) {
      prenotazioneSpiaggia.sdraio = prenotazioneSpiaggia.sdraio - 1;
    }
  }

  registerDate() {
    this.startDate = this.formDate.startDate;
    this.endDate = this.formDate.endDate;
    console.log(this.startDate);
    console.log(this.endDate);
    this.isDate = true;
    this.onGetOmbrelloni();
  }
}
