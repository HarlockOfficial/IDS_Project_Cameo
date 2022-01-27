import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../_services/shopping-cart.service';
import { Prenotazione } from '../interfaces/prenotazione';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';
import { Evento } from '../interfaces/evento';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  prenotazione!: Prenotazione;
  isPrenotazione: boolean = false;
  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.prenotazione = this.shoppingCartService.getPrenotazione();
    console.log(this.prenotazione)
    if (this.prenotazione.eventiPrenotatiList?.length! > 0 || this.prenotazione.spiaggiaPrenotazioniList?.length! > 0) {
      this.isPrenotazione = true;
    }
  }

  removeFromPrenotazione(item: PrenotazioneSpiaggia | Evento, tipo: string) {
    if (tipo == 'o') {
      this.prenotazione.spiaggiaPrenotazioniList?.forEach((element, index) => {
        if (element == item) {
          this.prenotazione.spiaggiaPrenotazioniList?.splice(index, 1);
        }
      });
    }
    else if (tipo == 'e') {
      this.prenotazione.eventiPrenotatiList?.forEach((element, index) => {
        if (element == item) {
          this.prenotazione.eventiPrenotatiList?.splice(index, 1);
        }
      });
    }

    if (this.prenotazione.eventiPrenotatiList?.length! == 0 && this.prenotazione.spiaggiaPrenotazioniList?.length! == 0) {
      this.isPrenotazione = false;
    }
  }
}
