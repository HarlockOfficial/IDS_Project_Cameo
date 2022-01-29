import {Injectable} from '@angular/core';
import {Evento} from '../interfaces/evento';
import {Prenotazione} from '../interfaces/prenotazione';
import {PrenotazioneSpiaggia} from '../interfaces/prenotazioneSpiaggia';
import {TokenStorageService} from './token-storage.service';
import {StatoPrenotazione} from '../interfaces/StatoPrenotazione';
import {HttpClient} from '@angular/common/http';
import {Order} from '../interfaces/order';
import {MenuElement} from '../interfaces/menuElement';
import {Observable} from "rxjs";

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
      this.ordine.menuElements = element as MenuElement[];
    }
  }

  checkoutCarrello(elemento: Prenotazione | Order, token: string) {
    const configs = { 'token': token };
    if ((<Prenotazione>elemento).spiaggiaPrenotazioniList !== undefined || (<Prenotazione>elemento).eventiPrenotatiList !== undefined) {
      console.log("aggiungo prenotazione");
      (<Prenotazione>elemento).date = ((<Prenotazione>elemento).date as Date).toISOString();
      (<Prenotazione>elemento).spiaggiaPrenotazioniList?.forEach((data) => {
        data.startDate = new Date(data.startDate).toISOString();
        data.endDate = new Date(data.endDate).toISOString();
      }); 
      return this.http.post(API + 'book', elemento, { headers: configs });
    }
    else {
      console.log("aggiungo menu")
      return this.http.post(API + 'order', elemento, { headers: configs });
    }
  }

  private getOrdineFromServer(token: string, status: string) {
    const configs = { 'token': token };
    return this.http.get<Order[]>(API + 'order/' + status, { headers: configs });
  }

  getOrdineOrdered(token: string) {
    return this.getOrdineFromServer(token, "ordered");
  }

  getOrdineInProgress(token: string) {
    return this.getOrdineFromServer(token, "in_progress");
  }

  getOrdineFinished(token: string) {
    return this.getOrdineFromServer(token, "finished");
  }

  getOrdineDelivered(token: string) {
    return this.getOrdineFromServer(token, "delivered");
  }

  getOrdinePaid(token: string) {
    return this.getOrdineFromServer(token, "paid");
  }

  getPrenotazione() {
    return this.prenotazione;
  }

  getOrdine() {
    return this.ordine;
  }

  updateOrderStatus(order: Order, token: string) {
    const configs = { 'token': token };
    return this.http.put<Order>(API + 'order/' + order.id, null, { headers: configs });
  }

  getAllPrenotazioni(token: string) {
    const configs = { 'token': token };
    return this.http.get<Prenotazione[]>(API + 'book', { headers: configs });
  }

  checkIn(prenotazione: Prenotazione, token: string): Observable<Prenotazione> {
    prenotazione.statoPrenotazione = StatoPrenotazione.PAGATO;
    return this.updatePrenotazione(prenotazione, token);
  }
  private updatePrenotazione(prenotazione: Prenotazione, token: string): Observable<Prenotazione> {
    const configs = { 'token': token };
    return this.http.put<Prenotazione>(API + 'book/', prenotazione, { headers: configs });
  }
}
