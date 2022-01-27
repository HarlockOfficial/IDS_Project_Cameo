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
    date: new Date(),
    eventiPrenotatiList: [],
    spiaggiaPrenotazioniList: [],
  }

  addItem(element: PrenotazioneSpiaggia | Evento) {
    if ((<PrenotazioneSpiaggia>element).ombrellone !== undefined) {
      const prenotazione = element as PrenotazioneSpiaggia;
      const res = this.prenotazione.spiaggiaPrenotazioniList!.some(e => e.ombrellone.id === prenotazione.ombrellone.id);
      if(res) return;
      this.prenotazione.spiaggiaPrenotazioniList!.push((<PrenotazioneSpiaggia>element));
    }
    else {
      const ev = element as Evento;
      const res = this.prenotazione.eventiPrenotatiList!.some(e => e.id == ev.id);
      if (res) return;
      this.prenotazione.eventiPrenotatiList!.push(ev);
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
