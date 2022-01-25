import { Component, OnInit } from '@angular/core';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { Ombrellone } from '../interfaces/ombrellone';

@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrls: ['./prenota.component.css']
})
export class PrenotaComponent implements OnInit {

  errorMessage = '';
  listaOmbrelloni!: Ombrellone[];
  listaOrdine: Ombrellone[] = [];
  isOrder: boolean = false;

  constructor(private ombrelloneService: OmbrelloneService) { }

  ngOnInit(): void {
    this.onGetOmbrelloni();
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
    if (this.listaOrdine.length == 0) {
      this.listaOrdine.push(ombrellone);
      this.removeFromListaOmbrelloni(ombrellone);
    }
    else {
      this.listaOrdine.forEach((element, index) => {
        if (element == ombrellone) {
          console.log("gia aggiunto");
        }
        else {
          this.listaOrdine.push(ombrellone);
          this.removeFromListaOmbrelloni(ombrellone);
        }
      });
    }
    this.isOrder = true;
  }

  removeFromOrder(ombrellone: Ombrellone) {
    this.listaOrdine.forEach((element, index) => {
      if (element == ombrellone) {
        this.listaOrdine.splice(index, 1);
        this.listaOmbrelloni.push(ombrellone);
      }
    });
    if (this.listaOrdine.length == 0) {
      this.isOrder = false;
    }
  }

  addToCart() {

  }




}
