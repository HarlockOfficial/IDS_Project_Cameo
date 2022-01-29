import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

  getAllEventi(): Observable<Evento[]> | null {
    return this.http.get<Evento[]>(API + 'event', { headers: headerDict });
  }

  addEvento(evento: Evento, token: string): Observable<Evento> {
    const configs = { 'token': token };

    return this.http.post<Evento>(API + 'event', evento, { headers: configs });
  }

  deleteEvent(id:string, token:string): Observable<Evento> {
    const configs = { 'token': token };

    return this.http.delete<Evento>(API + 'event/' + id, { headers: configs });
  }
}
