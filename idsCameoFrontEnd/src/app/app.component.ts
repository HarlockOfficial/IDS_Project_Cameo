import { Component, OnInit } from '@angular/core';
import { AuthService } from './_services/auth.service';
import { TokenStorageService } from './_services/token-storage.service';
import { ProfileComponent } from './profile/profile.component';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'idsCameoFrontEnd';
  isLoggedIn = false;
  isAdmin = false;

  constructor(private profileComponent: ProfileComponent, private authService: AuthService, private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    if (this.profileComponent.isAdmin) {
      this.isAdmin == true;
    }
  }

}
