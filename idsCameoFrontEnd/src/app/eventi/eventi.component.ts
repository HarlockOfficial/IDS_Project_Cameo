import { Component, OnInit } from '@angular/core';
import { EventiService } from '../_services/eventi.service';
import { Evento } from '../interfaces/evento';
@Component({
  selector: 'app-eventi',
  templateUrl: './eventi.component.html',
  styleUrls: ['./eventi.component.css']
})
export class EventiComponent implements OnInit {

  listaEventi!: Evento[];
  listaOrdine: Evento[] = [];
  isEvento: boolean = false;

  constructor(private eventiService: EventiService) { }

  ngOnInit(): void {
    this.onGetAllEvents();
  }

  onGetAllEvents(): void {
    this.eventiService.allEventi()?.subscribe(
      data => {
        this.listaEventi = data as Evento[];
        console.log(this.listaEventi);
      }
    );
  }

  //Rimuove un ombrellone dalla lista degli ombrelloni prenotabili
  removeFromListaOmbrelloni(evento: Evento) {
    this.listaEventi.forEach((element, index) => {
      if (element == evento) this.listaEventi.splice(index, 1);
    });
  }

  addToOrder(evento: Evento) {
    if (this.listaOrdine.length == 0) {
      this.listaOrdine.push(evento);
      this.removeFromListaOmbrelloni(evento);
    }
    else {
      this.listaOrdine.forEach((element, index) => {
        if (element == evento) {
          console.log("gia aggiunto");
        }
        else {
          this.listaOrdine.push(evento);
          this.removeFromListaOmbrelloni(evento);
        }
      });
    }
    this.isEvento = true;
  }

  removeFromOrder(evento: Evento) {
    this.listaOrdine.forEach((element, index) => {
      if (element == evento) {
        this.listaOrdine.splice(index, 1);
        this.listaEventi.push(evento);
      }
    });
    if (this.listaOrdine.length == 0) {
      this.isEvento = false;
    }
  }

  addToCart() {

  }


}
