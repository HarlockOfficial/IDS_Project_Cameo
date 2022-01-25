import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Evento } from '../interfaces/evento';

const API = 'http://localhost:8080/';

const headerDict = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
  'Access-Control-Allow-Headers': 'Content-Type',
};

@Injectable({
  providedIn: 'root'
})
export class EventiService {

  constructor(private http: HttpClient) { }

  allEventi(): Observable<Evento[]> | null {
    return this.http.get<Evento[]>(API + 'event', { headers: headerDict });;
  }

  addEvento(evento: Evento, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'event', evento, { headers: configs });
  }
}
