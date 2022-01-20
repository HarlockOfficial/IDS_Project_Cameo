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
export class UserService {

  constructor(private http: HttpClient) { }

  //Ritorna le informazioni di un utente specifio 
  userInfo(token: string | null): Observable<any> | null {
    if (token == null) {
      return null;
    }
    const configs = { 'token': token };
    return this.http.get(API + 'user', { headers: configs });;
  }
  //{{url}}/user/{{$randomUUID}}
}
