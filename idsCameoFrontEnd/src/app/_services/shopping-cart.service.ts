import { Injectable } from '@angular/core';
import { Evento } from '../interfaces/evento';
import { Prenotazione } from '../interfaces/prenotazione';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';
import { TokenStorageService } from '../_services/token-storage.service';
import { StatoPrenotazione } from '../interfaces/StatoPrenotazione';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = 'http://localhost:8080/';

const headerDict = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
  'Access-Control-Allow-Headers': 'Content-Type',
};

const requestOptions = {
  headers: new Headers(headerDict),
};

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

  addItem(element: PrenotazioneSpiaggia | Evento) {
    if ((<PrenotazioneSpiaggia>element).ombrellone !== undefined) {
      this.prenotazione.spiaggiaPrenotazioniList?.push((<PrenotazioneSpiaggia>element));
    }
    else {
      this.prenotazione.eventiPrenotatiList?.push((<Evento>element));
    }
  }

  checkoutCarrello(prenotazione: Prenotazione, token: string) {
    console.log("PROVORICHEISTAPORCODI")
    const configs = { 'token': token };

    return this.http.post(API + 'book', prenotazione, { headers: configs });
  }

  getPrenotazione() {
    return this.prenotazione;
  }

  saveCart() {

  }

}
