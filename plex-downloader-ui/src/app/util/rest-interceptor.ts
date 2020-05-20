import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {Constants} from "./constants";
import {map} from "rxjs/operators";

@Injectable()
export class RestInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }


  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    console.log('testing - ' + req.url);

    //kick out whoever doesn't belong.
    if (!req.url.endsWith('/basiclogin') && !req.url.includes('/oAuth')) {
      // console.log('injecting auth token');
      if (req.url.includes('{{serverIp}}')) {
        let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
        // console.log('injecting server: ' + serverIp);

        req = req.clone({
          url: req.url.replace('{{serverIp}}', serverIp)
        })
      }

      const authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
      console.log('authToken ' + authToken);

      if (authToken === null || authToken.trim().length === 0) {
        console.log('Error, not signed in. Returning back to login page.');
        this.router.navigate(['/']);
        return;
      } else {

        let headers = new HttpHeaders().set('Authorization', 'Bearer ' + authToken);


        req = req.clone({
          headers: headers
        });
      }
    }

    console.log("connecting...Headers: " + req.headers.get('Authorization'))

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
