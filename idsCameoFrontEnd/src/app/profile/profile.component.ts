import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { User } from '../interfaces/user';
import { Prenotazione } from '../interfaces/prenotazione';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser!: User;
  errorMessage = '';
  myPrenotazioni: Prenotazione[] = [];
  isAdmin = false;

  constructor(private userService: UserService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.tokenStorage.getUser()!;
    this.onGetUser();
    this.onGetMyPrenotazioni();
  }

  onGetUser(): void {
    this.userService.userInfo(this.tokenStorage.getToken())?.subscribe(
      data => {
        const myUser = this.tokenStorage.getUser()!;
        const filteredData = data.find(e => e.username == myUser.username)!;
        this.currentUser = filteredData as User;
        this.tokenStorage.saveUser(this.currentUser);
        if (filteredData.role == 'ADMIN') {
          this.isAdmin = true;
        }
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

  onGetMyPrenotazioni() {
    this.userService.myPrenotazioni(this.tokenStorage.getToken()!)?.subscribe(
      data => {
        this.myPrenotazioni = data;
      }
    )
  }

  reloadPage(): void {
    window.location.reload();
  }
}
