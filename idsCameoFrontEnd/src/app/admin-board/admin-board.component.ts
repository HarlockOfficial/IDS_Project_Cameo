import { Component, OnInit } from '@angular/core';
import { Ombrellone } from '../interfaces/ombrellone';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Evento } from '../interfaces/eventi';
import { EventiService } from '../_services/eventi.service';
import { MenuSection } from '../interfaces/menuSection';
import { MenuService } from '../_services/menu.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-admin-board',
  templateUrl: './admin-board.component.html',
  styleUrls: ['./admin-board.component.css']
})
export class AdminBoardComponent implements OnInit {

  formOmbrellone: any = {
    ombrelloneRowNumber: null,
    ombrelloneColumnNumber: null,
    prezzo: null,
    dataInizio: null,
    dataFine: null,
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

  constructor(private menuService: MenuService, private eventService: EventiService, private ombrelloneService: OmbrelloneService, private tokenStorage: TokenStorageService, private router: Router) { }

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
  }

  onAddOmbrellone() {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      const newOmbrellone: Ombrellone = {
        ombrelloneRowNumber: this.formOmbrellone.ombrelloneRowNumber,
        ombrelloneColumnNumber: this.formOmbrellone.ombrelloneColumnNumber,
        prezzo: this.formOmbrellone.prezzo,
        dataInizio: new Date(this.formOmbrellone.dataInizio),
        dataFine: new Date(this.formOmbrellone.dataFine)
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

}
