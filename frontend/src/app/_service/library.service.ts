import { Injectable } from '@angular/core';
import { parseString } from 'xml2js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {AlertifyService} from './alertify.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  baseUrl = 'https://plex.tv/api';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/x-www-form-urlencoded',
      'X-Plex-Product' : 'PlexDownloader',
      'X-Plex-Client-Identifier' : 'PC',
      'X-Plex-Version' : '0.0.1'
    })
  };

  constructor(private  http: HttpClient, private router: Router, private alertify: AlertifyService) { }

  retrievePlexResources(authToken: any) {
    console.log('calling library');
    this.httpOptions.headers = this.httpOptions.headers.append('Authorization', 'Basic ' + authToken);
    console.log('headers: ' + this.httpOptions.headers.get('Authorization'));


    return this.http.get(this.baseUrl + '/resources', this.httpOptions)
      .pipe(
        map((response: any) => {

          this.alertify.message('Works!');
          console.log('worked22222!');
          /*parseString(response, (err, result) => {
            console.dir(result);
          });*/

        })
      );
  }
}
