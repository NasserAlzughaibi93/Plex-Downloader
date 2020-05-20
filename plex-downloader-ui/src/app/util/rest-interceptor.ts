import {Injectable} from '@angular/core';
import {
  HttpClient,
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor, HttpParams,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';

import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {Constants} from "./constants";
import {map} from "rxjs/operators";
import { JwtHelperService } from "@auth0/angular-jwt";
import {User} from "../models/user/user.model";

@Injectable()
export class RestInterceptor implements HttpInterceptor {

  constructor(private router: Router, private http: HttpClient) {
  }


  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    //kick out whoever doesn't belong.
    if (!req.url.endsWith('/basiclogin') && !req.url.includes('/oAuth') && !req.url.includes('/refreshToken')) {
      // console.log('injecting auth token');
      if (req.url.includes('{{serverIp}}')) {
        let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
        // console.log('injecting server: ' + serverIp);

        req = req.clone({
          url: req.url.replace('{{serverIp}}', serverIp)
        })
      }

      const authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

      if (authToken === null || authToken.trim().length === 0) {
        console.log('Error, not signed in. Returning back to login page.');
        this.router.navigate(['/']);
        return;
      } else {

        const helper = new JwtHelperService();
        const expirationDate = helper.getTokenExpirationDate(authToken);

        //Get token date difference in hours
        let diff = (expirationDate.getTime() - Date.now()) / 1000 / 60 / 60;
        //console.log('Token expiration date: ' + diff);
        if (diff <= 24 && diff > 0) {
          console.log('refreshing...');
          let headers = new HttpHeaders().set('Authorization', 'Bearer ' + authToken);
          this.http.get(Constants.PLEX_DOWNLOADER_BASE_URL + '/settings/refreshToken', {headers}).pipe(
            map((user: User) => {
              localStorage.setItem(Constants.PLEX_AUTH_TOKEN, user.jwtToken);
              return user;
            })
          ).subscribe();
        }

        let headers = new HttpHeaders().set('Authorization', 'Bearer ' + authToken);


        req = req.clone({
          headers: headers
        });
      }
    }

    return next.handle(req).pipe(
      map(resp => {

        if (resp instanceof HttpResponse) {
          //TODO add logout logic for when token is expired.
          if (resp.status === 401) {
            this.router.navigate(['/']);
            localStorage.clear();
          }
          return  resp;
        }
      })
    );

  }
}
