import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './routes';
import {AlertifyService} from './_service/alertify.service';
import {LoginService} from './_service/login.service';
import {LibraryService} from './_service/library.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatTabsModule
  ],
  providers: [
    LoginService,
    AlertifyService,
    LibraryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
