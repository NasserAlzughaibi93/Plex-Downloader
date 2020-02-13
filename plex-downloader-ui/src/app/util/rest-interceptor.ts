import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpHandler, HttpRequest, HttpEvent, HttpResponse, HttpParams}
  from '@angular/common/http';

import {Observable} from 'rxjs';
import {Router} from "@angular/router";
import {Constants} from "./constants";

@Injectable()
export class RestInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }


  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    //kick out whoever doesn't belong.
    if (!req.url.endsWith('/basiclogin') && !req.url.includes('/oAuth')) {
      console.log('injecting auth token');
      if (req.url.includes('{{serverIp}}')) {
        let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
        console.log('injecting server: ' + serverIp);

        req = req.clone({
          url: req.url.replace('{{serverIp}}', serverIp)
        })
      }

      let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

      if (authToken === null || authToken.trim().length === 0) {
        console.log('Error, not signed in. Returning back to login page.');
        this.router.navigate(['/']);
        return;
      } else {
        req = req.clone({
          headers: req.headers.append('Authorization', 'Bearer ' + authToken)
        });
      }
    }

    return next.handle(req);

  }
}
