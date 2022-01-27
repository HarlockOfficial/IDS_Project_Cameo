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

  constructor(private ombrelloneService: OmbrelloneService, private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.onGetOmbrelloni();
    console.log(this.listaOmbrelloni);
  }

  onGetOmbrelloni(): void {
    this.ombrelloneService.allOmbrelloni()?.subscribe(
      data => {
        this.listaOmbrelloni = data;
        console.log(this.listaOmbrelloni);
      },
      err => {
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
      this.listaOrdine.push(newPrenotazioneSpiaggia);
      this.removeFromListaOmbrelloni(ombrellone);
    }
    else {
      this.listaOrdine.forEach((element, index) => {
        if (element.ombrellone == ombrellone) {
          console.log("gia aggiunto");
        }
        else {
          this.listaOrdine.push(newPrenotazioneSpiaggia);
          this.removeFromListaOmbrelloni(ombrellone);
        }
      });
    }
    this.isOrder = true;
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
}
