import { Component, OnInit } from '@angular/core';
import { MenuElement } from '../interfaces/menuElement';
import { MenuSection } from '../interfaces/menuSection';
import { MenuService } from '../_services/menu.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  listaMenu!: Map<MenuSection, MenuElement[]>;

  constructor(private menuService: MenuService) { }

  ngOnInit(): void {
    this.onGetMenu();
  }

  onGetMenu(): void {
    this.menuService.allMenu()?.subscribe(
      data => {
        this.listaMenu = data;
        console.log(this.listaMenu);
        for (let x in this.listaMenu) {
          console.log(x);
        }
      }
    );
  }

}
