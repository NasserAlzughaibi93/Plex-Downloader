import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {SettingsComponent} from "./settings/settings.component";
import {AboutComponent} from "./settings/about/about.component";
import {SearchComponent} from "./search/search.component";
import {AuthGuard} from "./_guards/auth.guard";
import {LibraryComponent} from "./library/library.component";
import {SystemSettingsComponent} from "./settings/system-settings/system-settings.component";

export const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {
    path: '',
    runGuardsAndResolvers: 'always',
    canActivate: [AuthGuard],
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'settings', component: SettingsComponent},
      {path: 'about', component: AboutComponent},
      {path: 'system-settings', component: SystemSettingsComponent},
      {path: 'search', component: SearchComponent},
      {path: 'library', component: LibraryComponent},
    ],
  },
  {path: '**', redirectTo: '/home', pathMatch: 'full'}
];
