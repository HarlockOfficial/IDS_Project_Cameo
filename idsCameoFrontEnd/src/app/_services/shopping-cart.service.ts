import { Injectable } from '@angular/core';
import { CartItem } from '../interfaces/cartItem';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor() { }

  cartList!: CartItem[];

  addItem(type: string, object: any, price: number) {
    const newCartItem: CartItem = {
      type: type,
      object: object,
      price: price,
    };
    this.cartList.push(newCartItem);
  }

  //TODO; REVIEW
  removeItem(cartItem: CartItem) {
    this.cartList.forEach((element, index) => {
      if (element.object == cartItem.object) {
        this.cartList.splice(index, 1);
      }
    });
  }


  saveCart() {

  }

  checkOutCart() {
    this.cartList.forEach((element, index) => {
      this.cartList.splice(index, 1);
    });
  }

}
