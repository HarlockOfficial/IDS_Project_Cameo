import { Injectable } from '@angular/core';
import { CartItem } from '../interfaces/cartItem';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor() { }

  cartList: CartItem[] = [];

  addItem(object: any) {
    const newCartItem: CartItem = {
      object: object,
    };
    this.cartList.push(newCartItem);
  }

  removeItem(cartItem: CartItem) {
    this.cartList.forEach((element, index) => {
      if (element.object == cartItem.object) {
        this.cartList.splice(index, 1);
      }
    });
  }

  getCartList() {
    return this.cartList;
  }

  saveCart() {

  }

  checkOutCart() {
    this.cartList.forEach((element, index) => {
      this.cartList.splice(index, 1);
    });
  }

}
