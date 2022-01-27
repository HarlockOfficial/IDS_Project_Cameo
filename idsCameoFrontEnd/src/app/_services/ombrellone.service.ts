import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
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
  allFreeOmbrelloni(startDate: Date, endDate: Date): Observable<Ombrellone[]> | null {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    let params = new HttpParams()
      .append('fromDate', start)
      .append('toDate', end);
    return this.http.get<Ombrellone[]>(API + 'ombrellone/free', { headers: headerDict, params: params});
  }

  addOmbrellone(ombrellone: Ombrellone, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'ombrellone', ombrellone, { headers: configs });
  }

}
