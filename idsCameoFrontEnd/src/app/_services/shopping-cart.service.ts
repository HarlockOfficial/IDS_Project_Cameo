import { Injectable } from '@angular/core';
import { Evento } from '../interfaces/evento';
import { Prenotazione } from '../interfaces/prenotazione';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';
import { TokenStorageService } from '../_services/token-storage.service';
import { StatoPrenotazione } from '../interfaces/StatoPrenotazione';
@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private tokenStorageService: TokenStorageService) { }

  prenotazione: Prenotazione = {
    user: this.tokenStorageService.getUser()!,
    statoPrenotazione: StatoPrenotazione.CARRELLO,
    date: new Date(1 / 11 / 1111),
    eventiPrenotatiList: [],
    spiaggiaPrenotazioniList: [],
  }

  addItem(element: PrenotazioneSpiaggia | Evento) {
    if ((<PrenotazioneSpiaggia>element).ombrellone !== undefined) {
      this.prenotazione.spiaggiaPrenotazioniList?.push((<PrenotazioneSpiaggia>element));
    }
    else {
      this.prenotazione.eventiPrenotatiList?.push((<Evento>element));
    }
  }

  /*
  removeItem(element: Prenotazione) {
    this.prenotazione.forEach((element, index) => {
      if (element.element == cartItem.element) {
        this.prenotazione.splice(index, 1);
      }
    });
  }*/

  getPrenotazione() {
    return this.prenotazione;
  }

  saveCart() {

  }

}
