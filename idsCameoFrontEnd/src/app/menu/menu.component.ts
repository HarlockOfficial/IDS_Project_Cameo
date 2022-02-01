import { Component, OnInit } from '@angular/core';
import { MenuElement } from '../interfaces/menuElement';
import { MenuSection } from '../interfaces/menuSection';
import { MenuService } from '../_services/menu.service';
import { ShoppingCartService } from '../_services/shopping-cart.service';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  listaMenu: Map<MenuSection, MenuElement[]> = new Map<MenuSection, MenuElement[]>();
  listaOrdine: MenuElement[] = [];
  isOrder: boolean = false;
  constructor(private shoppingCartService: ShoppingCartService, private menuService: MenuService) {
  }

  ngOnInit(): void {
    this.onGetMenu();
  }

  onGetMenu(): void {
    this.menuService.allMenu()?.subscribe(
      data => {
        for (let i = 0; i < data.length; i++) {
          const menuSection: MenuSection = {
            sectionName: data[i].section!.sectionName,
            isSectionVisible: data[i].section!.sectionVisible,
          };
          if (!menuSection.isSectionVisible) {
            continue;
          }
          let elemsList: MenuElement[] = [];
          const keys = this.listaMenu.keys();
          for (let key of keys) {
            if (key.sectionName === menuSection.sectionName) {
              elemsList = this.listaMenu.get(key)!;
              break;
            }
          }
          if (elemsList.some(e => e.name === data[i].name)) {
            continue;
          }
          elemsList.push(data[i]);
          this.listaMenu.set(menuSection, elemsList);
        }
      }
    );
  }

  addToOrder(element: MenuElement) {
    this.isOrder = true;
    this.listaOrdine.push(element);
  }

  removeFromOrder(elementToRemove: MenuElement) {
    this.listaOrdine.forEach((element, index) => {
      if (elementToRemove.id == element.id) {
        this.listaOrdine.splice(index, 1);
      }
    });

    if (this.listaOrdine.length == 0) {
      this.isOrder = false;
    }
  }

  addToCart() {
    this.shoppingCartService.addItem(this.listaOrdine);
    this.isOrder = false;
  }

}
