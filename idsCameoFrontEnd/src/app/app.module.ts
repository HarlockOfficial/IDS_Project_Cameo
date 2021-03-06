import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { PrenotaComponent } from './prenota/prenota.component';
import { AdminBoardComponent } from './admin-board/admin-board.component';
import { MenuComponent } from './menu/menu.component';
import { EventiComponent } from './eventi/eventi.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HomeComponent,
    PrenotaComponent,
    AdminBoardComponent,
    MenuComponent,
    EventiComponent,
    ShoppingCartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [LoginComponent, ProfileComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
  sticky: boolean = false;

}
