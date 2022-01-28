import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prenotazione } from '../interfaces/prenotazione';

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
export class PrenotazioneService {

  constructor(private http: HttpClient) { }

  addPrenotazione(prenotazione: Prenotazione, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'book', prenotazione, { headers: configs });
  }

  deletePrenotazione(id: string, token: string): Observable<any> {
    const configs = { 'token': token };
    return this.http.delete(API + `book/${id}`, { headers: configs });
  }

}
