import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent, HttpResponse }
  from '@angular/common/http';

import { Observable } from 'rxjs';
import {Router} from "@angular/router";

@Injectable()
export class RestInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}


  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    //kick out whoever doesn't belong.
    if (!req.url.endsWith('/basiclogin') && !req.url.endsWith('/oAuth')) {
      if (req.params.get('authToken')) {
        let authToken = req.params.get('authToken');
        if (authToken === null || authToken.trim().length === 0) {
          console.log('Error, not signed in. Returning back to login page.');
          this.router.navigate(['/']);
          return;
        }
      }
    }

    return next.handle(req);

  }
}
