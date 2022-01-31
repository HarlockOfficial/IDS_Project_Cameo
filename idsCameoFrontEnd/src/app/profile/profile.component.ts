import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { User } from '../interfaces/user';
import { Prenotazione } from '../interfaces/prenotazione';
import { Observable } from 'rxjs';
import { PrenotazioneService } from '../_services/prenotazione.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser!: User;
  errorMessage = '';
  myPrenotazioni!: Observable<Prenotazione[]>;
  isAdmin = false;
  isBar = false;
  isAccoglienza = false;
  isEventManager = false;

  constructor(private prenotazioneService: PrenotazioneService, private userService: UserService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.tokenStorage.getUser()!;
    this.onGetUser();
    this.onGetMyPrenotazioni();
    console.log(this.myPrenotazioni);
  }

  onGetUser(): void {
    this.userService.userInfo(this.tokenStorage.getToken())?.subscribe(
      data => {
        const myUser = this.tokenStorage.getUser()!;
        const filteredData = data.find(e => e.username == myUser.username)!;
        this.currentUser = filteredData as User;
        this.tokenStorage.saveUser(this.currentUser);
        console.log(this.currentUser);
        if (filteredData.role == 'ADMIN') {
          this.isAdmin = true;
        } else if (filteredData.role == 'BAR') {
          this.isBar = true;
        } else if (filteredData.role == 'RECEPTION') {
          this.isAccoglienza = true;
        } else if (filteredData.role == 'EVENT_MANAGER') {
          this.isEventManager = true;
        }
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

  onGetMyPrenotazioni() {
    this.myPrenotazioni = this.userService.myPrenotazioni(this.tokenStorage.getToken()!)!;
  }

  removePrenotazione(id: string) {
    this.prenotazioneService.deletePrenotazione(id, this.tokenStorage.getToken()!).subscribe(
      _ => {
        this.onGetMyPrenotazioni();
      }
    )
  }
}
