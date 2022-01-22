import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(username: string | User): void {
    let user: User;
    if (typeof (username) == 'string') {
      user = {
        id: null, role: '',
        name: '',
        surname: '',
        username: username,
        email: '',
        birthDate: new Date(1 / 11 / 1111),
      };
    }
    else {
      user = username;
    }
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): User | null {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

}
