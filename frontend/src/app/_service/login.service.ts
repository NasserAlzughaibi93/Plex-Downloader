import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {User} from '../models/user/user.model';
import {Router} from '@angular/router';
import {AlertifyService} from './alertify.service';
import {LibraryService} from './library.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  // baseUrl = 'https://plex.tv/';
  baseUrl = 'http://localhost:8081/api';
  //TODO update port

  constructor(
    private http: HttpClient,
    private router: Router,
    private alertify: AlertifyService,
    private libraryService: LibraryService) { }

  login(model: any) {

    // const authToken = btoa(model.username + ':' + model.password);

    // console.log('Token: ' + authToken);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded',
        'Access-Control-Allow-Origin': '*',
        // 'X-Plex-Product' : 'PlexDownloader',
        // 'X-Plex-Client-Identifier' : 'PC',
        // 'X-Plex-Version' : '0.0.1',
        // Authorization: 'Basic ' + authToken
      })
    };

    return this.http.post(this.baseUrl + '/basiclogin', model)
      .pipe(
        map((response: any) => {
          const user = new User().deserialize(response.user);

          this.alertify.message('Welcome, ' + user.username);
          console.log('Icon: ' + user.thumb);

          if (user) {
            localStorage.setItem('token', user.authToken);
            // localStorage.setItem('user', user);

            /*this.libraryService.retrievePlexResources(authToken).subscribe(() => {
              console.log('Success!');
            }, error => {
              console.log('error: ' + error);
            });*/
            this.router.navigate(['/home']);
          }
        })
      );
  }
}
