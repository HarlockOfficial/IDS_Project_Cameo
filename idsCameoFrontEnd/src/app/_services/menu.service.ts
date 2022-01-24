import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MenuSection } from '../interfaces/menuSection';

const API = 'http://localhost:8080/';

const headerDict = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
  'Access-Control-Allow-Headers': 'Content-Type',
};

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http: HttpClient) { }

  addMenuSection(menuSection: MenuSection, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'menu/section', menuSection, { headers: configs });
  }
}
