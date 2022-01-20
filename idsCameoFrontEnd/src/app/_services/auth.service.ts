import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/';

/*
const headers = new HttpHeaders().append('Content-Type', 'application/json');*/

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

  login(username: string, password: string): Observable<any> {
    let params = new HttpParams()
      .append('username', username)
      .append('password', password);
    return this.http.post(AUTH_API + 'login', null, { headers: headerDict, params: params })
  }

  register(username: string, name: string, surname: string, email: string, password: string, birthDate: Date): Observable<any> {
    return this.http.post(AUTH_API + 'register', {
      username,
      name,
      surname,
      email,
      password,
      birthDate
    }, { headers: headerDict });
  }

  logout(token: string | null): Observable<any> | null {
    console.log("test2");

    if (token == null) {
      console.log("test3");

      return null;
    }

    const headerDict2 = { 'token': token };
    console.log(headerDict2);

    const test = this.http.get(AUTH_API + 'logout', { headers: headerDict2 });
    return test;
  }
}
