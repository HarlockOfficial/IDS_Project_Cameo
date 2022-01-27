import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../_services/shopping-cart.service';
import { Prenotazione } from '../interfaces/prenotazione';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  prenotazione!: Prenotazione;

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.prenotazione = this.shoppingCartService.getPrenotazione();
  }

}
