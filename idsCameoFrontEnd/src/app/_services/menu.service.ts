import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MenuSection } from '../interfaces/menuSection';
import { MenuElement } from '../interfaces/menuElement';

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

  allMenu(): Observable<Map<MenuSection, MenuElement[]>> | null {
    return this.http.get<any>(API + 'menu');
  }

  addMenuSection(menuSection: MenuSection, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'menu/section', menuSection, { headers: configs });
  }

  addMenuElement(menuElement: MenuElement, token: string): Observable<any> {
    const configs = { 'token': token };

    return this.http.post(API + 'menu/element', menuElement, { headers: configs });
  }

  allSection(): Observable<MenuSection[]> {
    return this.http.get<MenuSection[]>(API + 'menu/section');
  }
}
