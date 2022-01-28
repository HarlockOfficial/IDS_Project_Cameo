import { Component, NgZone, OnInit } from '@angular/core';
import { Ombrellone } from '../interfaces/ombrellone';
import { OmbrelloneService } from '../_services/ombrellone.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Evento } from '../interfaces/evento';
import { EventiService } from '../_services/eventi.service';
import { MenuSection } from '../interfaces/menuSection';
import { MenuService } from '../_services/menu.service';
import { Router } from '@angular/router';
import { MenuElement } from '../interfaces/menuElement';
import { Observable } from 'rxjs';
import { UserService } from '../_services/user.service';
import { User } from '../interfaces/user';
import { Prenotazione } from '../interfaces/prenotazione';
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

  formRemoveMenuSection: any = {
    id: null,
  }

  formRoleChange: any = {
    newRole: null,
  }

  isAdmin!: boolean;
  isEventManager!: boolean;
  ombrelloneCreated!: boolean;
  eventCreated!: boolean;
  sectionCreated!: boolean;
  elementCreated!: boolean;
  eventDeleted!: boolean;
  sectionDeleted!: boolean;
  sectionCall!: Observable<MenuSection[]>;
  eventList!: Evento[];
  allUserList!: Observable<User[]>;

  constructor(private userService: UserService, private menuService: MenuService, private eventService: EventiService, private ombrelloneService: OmbrelloneService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {

    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      this.isAdmin = true;
    } else if (this.tokenStorage.getUser()?.role == "EVENT_MANAGER") {
      this.isEventManager = true;
    } else {
      this.router.navigate(['/home']);
    }

    this.onGetAllSection(this.tokenStorage.getToken()!);
    this.onGetAllEvents();
    this.onGetAllUser();
  }

  onGetAllSection(token: string) {
    this.sectionCall = this.menuService.allSection(token);
  }

  onGetAllEvents() {
    this.eventService.getAllEventi()!.subscribe(
      data => {
        console.log(data);
        this.eventList = data;
      }
    );
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
        _ => {
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
        _ => {
          this.eventCreated = true;
          this.onGetAllEvents();
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
        _ => {
          this.sectionCreated = true;
          this.onGetAllSection(token);
        }
      );
    }
  }

  onCreateMenuElement() {
    if (this.tokenStorage.getUser()?.role == "ADMIN") {
      this.sectionCall.forEach(e => {
        const x = e.filter(elem => (elem.id == this.formMenuElement.menuSection))
        const sect = x[0] as MenuSection;

        const newMenuElement: MenuElement = {
          name: this.formMenuElement.name,
          description: this.formMenuElement.description,
          price: this.formMenuElement.price,
          section: sect!,
          isElementVisible: true,
        };
        console.log(newMenuElement);
        const token = this.tokenStorage.getToken()!;

        this.menuService.addMenuElement(newMenuElement, token).subscribe(
          _ => {
            this.elementCreated = true;
            this.onGetAllSection(token);
          }
        );
        return;
      });
    }
  }

  onDeleteEvent() {
    if (this.tokenStorage.getUser()?.role == "ADMIN" || this.tokenStorage.getUser()?.role == "EVENT_MANAGER") {
      const token = this.tokenStorage.getToken()!;
      console.log(this.formEvento.evento);
      this.eventService.deleteEvent(this.formEvento.evento, token).subscribe(
        _ => {
          this.eventDeleted = true;
          this.onGetAllEvents();
        }
      );
    }
  }

  onGetAllUser() {
    this.allUserList = this.userService.userInfo(this.tokenStorage.getToken())!;
  }

  changeUserRole(user: User) {
    user.role = this.formRoleChange.role;
    this.userService.changeUserRole(user, this.tokenStorage.getToken()!).subscribe(
      data => {
        console.log(data);
      }
    );
  }

  onDeleteSection() {
    this.menuService.deleteSection(this.formRemoveMenuSection.id, this.tokenStorage.getToken()!).subscribe(
      data => {
        console.log(data);
      }
    )
  }

}
