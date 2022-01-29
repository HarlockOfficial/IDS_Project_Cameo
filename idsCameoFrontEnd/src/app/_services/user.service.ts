import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';
import { Prenotazione } from '../interfaces/prenotazione';
const API = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  //Ritorna le informazioni di tutti gli utenti credo..?
  userInfo(token: string | null): Observable<User[]> | null {
    if (token == null) {
      return null;
    }
    const configs = { 'token': token };
    return this.http.get<User[]>(API + 'user', { headers: configs });
  }


  //Ritorna le prenotazioni dello speicifo utente
  myPrenotazioni(token: string) {
    if (token == null) {
      return null;
    }
    const configs = { 'token': token };
    return this.http.get<Prenotazione[]>(API + 'book', { headers: configs });
  }

  changeUserRole(user: User, token: string) {
    const configs = { 'token': token };

    return this.http.put(API + 'user', user, { headers: configs });
  }

  deleteUser(id: string, token: string): Observable<any> {
    const configs = { 'token': token };
    return this.http.delete(API + `user/${id}`, { headers: configs });
  }

}
