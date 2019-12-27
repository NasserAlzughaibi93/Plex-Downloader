import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';

export const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {
    path: '',
    runGuardsAndResolvers: 'always',
    // canActivate: [AuthGuard],
    children: [
      {path: 'home', component: HomeComponent},
    ],

  },
  {path: '**', redirectTo: '', pathMatch: 'full'}
];
