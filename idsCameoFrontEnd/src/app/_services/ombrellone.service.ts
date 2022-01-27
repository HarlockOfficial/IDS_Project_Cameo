import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ombrellone } from '../interfaces/ombrellone';

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
export class OmbrelloneService {

  constructor(private http: HttpClient) { }

  //Restituisce tutti gli ombrelloni in una certa da di inizio e fine
  allOmbrelloni(): Observable<Ombrellone[]> | null {
    return this.http.get<Ombrellone[]>(API + 'ombrellone/all', { headers: headerDict });;
  }

  addOmbrellone(ombrellone: Ombrellone, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'ombrellone', ombrellone, { headers: configs });
  }

}
