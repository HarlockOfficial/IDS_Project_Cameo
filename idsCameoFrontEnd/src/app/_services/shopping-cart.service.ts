import { Injectable } from '@angular/core';
import { Evento } from '../interfaces/evento';
import { Prenotazione } from '../interfaces/prenotazione';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';
import { TokenStorageService } from '../_services/token-storage.service';
import { StatoPrenotazione } from '../interfaces/StatoPrenotazione';
import { HttpClient } from '@angular/common/http';
import { Order } from '../interfaces/order';
import { MenuElement } from '../interfaces/menuElement';
const API = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private tokenStorageService: TokenStorageService, private http: HttpClient) { }

  prenotazione: Prenotazione = {
    user: this.tokenStorageService.getUser()!,
    statoPrenotazione: StatoPrenotazione.CARRELLO,
    date: new Date(),
    eventiPrenotatiList: [],
    spiaggiaPrenotazioniList: [],
  }

  ordine: Order = {
    dateTime: new Date(),
    orderStatus: "ORDERED",
    menuElements: [],
    user: this.tokenStorageService.getUser()!,
    ombrellone: undefined,
  }

  addItem(element: PrenotazioneSpiaggia | Evento | MenuElement[]) {
    if ((<PrenotazioneSpiaggia>element).ombrellone !== undefined) {
      const prenotazione = element as PrenotazioneSpiaggia;
      const res = this.prenotazione.spiaggiaPrenotazioniList!.some(e => e.ombrellone.id === prenotazione.ombrellone.id);
      if (res) return;
      this.prenotazione.spiaggiaPrenotazioniList!.push((<PrenotazioneSpiaggia>element));
    }
    else if ((<Evento>element).location !== undefined) {
      const ev = element as Evento;
      const res = this.prenotazione.eventiPrenotatiList!.some(e => e.id == ev.id);
      if (res) return;
      this.prenotazione.eventiPrenotatiList!.push(ev);
    }
    else {
      const menuElement = element as MenuElement[];
      this.ordine.menuElements = menuElement;
    }
  }

  checkoutCarrello(elemento: Prenotazione | Order, token: string) {
    const configs = { 'token': token };
    if ((<Prenotazione>elemento).spiaggiaPrenotazioniList !== undefined || (<Prenotazione>elemento).eventiPrenotatiList !== undefined) {
      console.log("aggiungo prenotazione")

      return this.http.post(API + 'book', elemento, { headers: configs });
    }
    else {
      console.log("aggiungo menu")
      return this.http.post(API + 'order', elemento, { headers: configs });
    }
  }

  getPrenotazione() {
    return this.prenotazione;
  }

  getOrdine() {
    return this.ordine;
  }
}
