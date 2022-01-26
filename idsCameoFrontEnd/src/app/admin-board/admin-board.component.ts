import { Component, NgZone, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Ombrellone } from '../interfaces/ombrellone';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Evento } from '../interfaces/evento';
import { EventiService } from '../_services/eventi.service';
import { MenuSection } from '../interfaces/menuSection';
import { MenuService } from '../_services/menu.service';
import { Router } from '@angular/router';
import { MenuElement } from '../interfaces/menuElement';
import { Observable, of } from 'rxjs';
import { map, mapTo, tap } from 'rxjs/operators'

@Component({
  selector: 'app-admin-board',
  templateUrl: './admin-board.component.html',
  styleUrls: ['./admin-board.component.css']
})
export class AdminBoardComponent implements OnInit {

  formOmbrellone: any = {
    numberRow: null,
    numberColumn: null,
    price: null,
    startDate: null,
    endDate: null,
  };

  formEvento: any = {
    name: null,
    location: null,
    date: null,
    description: null,
    price: null,
  }

  formMenuSection: any = {
    sectionName: null,
    isSectionVisible: null,
  }

  formMenuElement: any = {
    name: null,
    description: null,
    price: null,
    menuSection: null,
    isElementVisible: null,
  }

  isAdmin!: boolean;
  isEventManager!: boolean;
  ombrelloneCreated!: boolean;
  eventCreated!: boolean;
  sectionCreated!: boolean;
  elementCreated!: boolean;
  sectionCall!: Observable<MenuSection[]>;

  constructor(private menuService: MenuService, private eventService: EventiService, private ombrelloneService: OmbrelloneService, private tokenStorage: TokenStorageService, private router: Router, private ngZone: NgZone) {

  }

  ngOnInit(): void {

    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      this.isAdmin = true;
    }
    else if (this.tokenStorage.getUser()?.role == "EVENT_MANAGER") {
      this.isEventManager = true;
    }
    else {
      this.router.navigate(['/home']);
    }

    this.onGetAllSection(this.tokenStorage.getToken()!);
  }

  onGetAllSection(token: string) {
    this.sectionCall = this.menuService.allSection(token);
  }

  onAddOmbrellone() {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      const newOmbrellone: Ombrellone = {
        numberRow: this.formOmbrellone.numberRow,
        numberColumn: this.formOmbrellone.numberColumn,
        price: this.formOmbrellone.price,
        startDate: new Date(this.formOmbrellone.startDate),
        endDate: new Date(this.formOmbrellone.endDate),
      };

      const token = this.tokenStorage.getToken()!;

      this.ombrelloneService.addOmbrellone(newOmbrellone, token).subscribe(
        data => {
          this.ombrelloneCreated = true;
        }
      );
    }
  }

  onCreateEvent() {
    if (this.tokenStorage.getUser()?.role == "ADMIN" || this.tokenStorage.getUser()?.role == "EVENT_MANAGER") {
      const newEvent: Evento = {
        name: this.formEvento.name,
        location: this.formEvento.location,
        date: new Date(this.formEvento.date),
        description: this.formEvento.description,
        price: this.formEvento.price,
      };

      const token = this.tokenStorage.getToken()!;

      this.eventService.addEvento(newEvent, token).subscribe(
        data => {
          this.eventCreated = true;
        }
      );
    }
  }

  onCreateMenuSection() {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      const newMenuSection: MenuSection = {
        sectionName: this.formMenuSection.sectionName,
        isSectionVisible: true,
      };

      const token = this.tokenStorage.getToken()!;

      this.menuService.addMenuSection(newMenuSection, token).subscribe(
        data => {
          this.sectionCreated = true;
        }
      );
    }
  }

  onCreateMenuElement() {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      this.sectionCall.forEach(e => {
        var x = e.filter(elem => (elem.id == this.formMenuElement.menuSection))
        const sect = x[0] as MenuSection;

        const newMenuElement: MenuElement = {
          name: this.formMenuElement.name,
          description: this.formMenuElement.description,
          price: this.formMenuElement.price,
          menuSection: sect!,
          isElementVisible: true,
        };
        console.log(newMenuElement);
        const token = this.tokenStorage.getToken()!;

        this.menuService.addMenuElement(newMenuElement, token).subscribe(
          data => {
            this.elementCreated = true;
          }
        );
        return;
      });
    }
  }

  /*
  refreshList() {
    const div = document.getElementById("listSection")!;
    console.log(div);
    let html = "";
    this.sectionList.forEach((val) => {
      html += `
        <option value='${val.sectionName}'>${val.sectionName}</option>
      `
    });
    console.log(html);
    div.innerHTML = html;
  } */

}
