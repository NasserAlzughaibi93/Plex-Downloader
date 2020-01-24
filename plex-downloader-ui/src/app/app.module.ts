import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './routes';
import {AlertifyService} from './_service/alertify.service';
import {LoginService} from './_service/login.service';
import {LibraryService} from './_service/library.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {RestInterceptor} from "./util/rest-interceptor";
import { SettingsComponent } from './settings/settings.component';
import { AboutComponent } from './settings/about/about.component';
import { SearchComponent } from './search/search.component';
import {NgbModule, NgbPopoverModule} from "@ng-bootstrap/ng-bootstrap";
import { SeriesPanelComponent } from './search/series-panel/series-panel.component';
import { MediaCardComponent } from './home/media-card/media-card.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent,
    SettingsComponent,
    AboutComponent,
    SearchComponent,
    SeriesPanelComponent,
    MediaCardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes, {onSameUrlNavigation: 'reload'}),
    BrowserAnimationsModule,
    MatTabsModule,
    NgbModule
  ],
  providers: [
    LoginService,
    AlertifyService,
    LibraryService,
    {provide: HTTP_INTERCEPTORS, useClass: RestInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
