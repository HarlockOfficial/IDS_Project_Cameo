import {Component, OnInit} from '@angular/core';
import {MenuElement} from '../interfaces/menuElement';
import {MenuSection} from '../interfaces/menuSection';
import {MenuService} from '../_services/menu.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  listaMenu: Map<MenuSection, MenuElement[]> = new Map<MenuSection, MenuElement[]>();

  constructor(private menuService: MenuService) {
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
          if (this.listaMenu.has(menuSection)) {
            elemsList = this.listaMenu.get(menuSection)!;
          }
          elemsList.push(data[i]);
          this.listaMenu.set(menuSection, elemsList);
        }
      }
    );
  }

}
