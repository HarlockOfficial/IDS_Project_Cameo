import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../_services/shopping-cart.service';
import { Prenotazione } from '../interfaces/prenotazione';
import { PrenotazioneSpiaggia } from '../interfaces/prenotazioneSpiaggia';
import { Evento } from '../interfaces/evento';
import { StatoPrenotazione } from '../interfaces/StatoPrenotazione';
import { TokenStorageService } from '../_services/token-storage.service';
import { Order } from '../interfaces/order';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  prenotazione!: Prenotazione;
  ordine!: Order;
  isPrenotazione: boolean = false;
  isOrdine: boolean = false;
  isAuth: boolean = false;
  constructor(private shoppingCartService: ShoppingCartService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.prenotazione = this.shoppingCartService.getPrenotazione();
    this.ordine = this.shoppingCartService.getOrdine();
    if (this.ordine.menuElements?.length! > 0) {
      this.isOrdine = true;
    }
    if (this.prenotazione.eventiPrenotatiList?.length! > 0 || this.prenotazione.spiaggiaPrenotazioniList?.length! > 0) {
      this.isPrenotazione = true;
    }
    if (this.tokenStorageService.getToken() != undefined && this.tokenStorageService.getToken() != null) {
      this.isAuth = true;
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

  checkOutCarrello() {
    //Ho sia prenotazione che ordine
    if (this.isPrenotazione && this.isOrdine) {
      this.checkoutPrenotazione();
      this.checkoutOrdine();
    }
    else if (this.isPrenotazione) {
      this.checkoutPrenotazione();
    }
    else if (this.isOrdine) {
      this.checkoutOrdine()
    }
  }

  checkoutPrenotazione() {
    this.prenotazione.statoPrenotazione = StatoPrenotazione.CONFERMATO;
    this.shoppingCartService.checkoutCarrello(this.prenotazione, this.tokenStorageService.getToken()!).subscribe(
      _ => {
        this.reloadPage();
      }
    );
    this.prenotazione = {
      user: this.tokenStorageService.getUser()!,
      statoPrenotazione: StatoPrenotazione.CARRELLO,
      date: new Date(),
      eventiPrenotatiList: [],
      spiaggiaPrenotazioniList: [],
    }
    this.isPrenotazione = false;
    this.reloadPage();
  }

  checkoutOrdine() {
    this.ordine.orderStatus = "ORDERED";
    this.shoppingCartService.checkoutCarrello(this.ordine, this.tokenStorageService.getToken()!).subscribe(
      _ => {
        this.reloadPage();
      }
    );
    this.ordine = {
      dateTime: new Date(),
      orderStatus: "ORDERED",
      menuElements: [],
      user: this.tokenStorageService.getUser()!,
      ombrellone: undefined,
    }
    this.isOrdine = false;
    this.reloadPage();
  }

  reloadPage(): void {
    window.location.reload();
  }
}
