import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {SettingsComponent} from "./settings/settings.component";
import {AboutComponent} from "./settings/about/about.component";

export const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {
    path: '',
    runGuardsAndResolvers: 'always',
    // canActivate: [AuthGuard],
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'settings', component: SettingsComponent},
      {path: 'about', component: AboutComponent},
    ],

  },
  {path: '**', redirectTo: '/home', pathMatch: 'full'}
];
