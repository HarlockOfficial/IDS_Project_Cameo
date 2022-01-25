import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = 'http://localhost:8080/';

const headerDict = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
  'Access-Control-Allow-Headers': 'Content-Type',
};

const requestOptions = {
  headers: new Headers(headerDict),
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  //Chiamata per effettuare login
  login(username: string, password: string): Observable<any> {
    let params = new HttpParams()
      .append('username', username)
      .append('password', password);
    return this.http.post(API + 'login', null, { headers: headerDict, params: params })
  }

  register(username: string, name: string, surname: string, email: string, password: string, birthDate: Date): Observable<any> {
    return this.http.post(API + 'register', {
      username,
      name,
      surname,
      email,
      password,
      birthDate
    }, { headers: headerDict });
  }

  logout(token: string | null): Observable<any> | null {
    if (token == null) {
      return null;
    }
    const configs = { 'token': token };
    return this.http.get(API + 'logout', { headers: configs });;
  }
}
