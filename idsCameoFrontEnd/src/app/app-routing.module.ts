import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { PrenotaComponent } from './prenota/prenota.component';
import { AdminBoardComponent } from './admin-board/admin-board.component';
import { EventiComponent } from './eventi/eventi.component';
import { MenuComponent } from './menu/menu.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'prenota', component: PrenotaComponent },
  { path: 'dashboard', component: AdminBoardComponent },
  { path: 'eventi', component: EventiComponent },
  { path: 'menu', component: MenuComponent },
  { path: 'cart', component: ShoppingCartComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
