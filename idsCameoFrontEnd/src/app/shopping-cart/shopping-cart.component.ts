import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../_services/shopping-cart.service';
import { CartItem } from '../interfaces/cartItem';
@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  cartList: CartItem[] = [];

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.cartList = this.shoppingCartService.getCartList();
  }

  removeFromOrder(item: CartItem) {
    this.shoppingCartService.removeItem(item);
    this.cartList = this.shoppingCartService.getCartList();
  }


}
