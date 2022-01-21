import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ombrellone } from '../ombrellone';
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

  //Restituisce tutti gli ombrelloni
  allOmbrelloni(): Observable<Ombrellone[]> | null {
    return this.http.get<Ombrellone[]>(API + 'ombrellone/all', { headers: headerDict });;
  }

}