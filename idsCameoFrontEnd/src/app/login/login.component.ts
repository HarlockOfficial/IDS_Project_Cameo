import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { ProfileComponent } from '../profile/profile.component';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {
    username: null,
    password: null
  };

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string | undefined;

  constructor(private profileComponent: ProfileComponent, private authService: AuthService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser()?.role;
    }
    this.profileComponent.onGetUser();
  }

  onSubmit(): void {
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(username);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser()?.role;

        //Prendo i dati dell'utente
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  onLogout(): void {
    this.authService.logout(this.tokenStorage.getToken())?.subscribe(
      data => {
        this.tokenStorage.signOut();
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }
}
