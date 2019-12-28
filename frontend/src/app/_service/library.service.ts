import { Injectable } from '@angular/core';
import { parseString } from 'xml2js';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {AlertifyService} from './alertify.service';
import {map} from 'rxjs/operators';
import {Constants} from "../util/constants";
import {Convert, MediaContainer} from "../models/mediacontainer.model";
import {Observable} from "rxjs";
import {Video} from "../models/video.model";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  baseUrl = Constants.PLEX_DOWNLOADER_BASE_URL;

  constructor(private  http: HttpClient, private router: Router, private alertify: AlertifyService) { }

  retrievePlexResources(authToken: any) : Observable<MediaContainer> {

    if (authToken == null) {
      console.log("Getting token from local storage");
      authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    }

    console.log('calling library + auth token: ' + authToken);
    /*this.httpOptions.headers = this.httpOptions.headers.append('Authorization', 'Basic ' + authToken);
    console.log('headers: ' + this.httpOptions.headers.get('Authorization'));*/

    const params = new HttpParams().set('authToken', authToken);

    return this.http.get<MediaContainer>(this.baseUrl + '/plexResources', {params})
      .pipe(
        map((response: any) => {

          this.alertify.message('Works!');

          return response;
        })
      );
  }

  retrieveLibraryOnDeck(serverIp: string) : Observable<Video[]> {

    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);


    console.log('calling library + auth token: ' + authToken);
    /*this.httpOptions.headers = this.httpOptions.headers.append('Authorization', 'Basic ' + authToken);
    console.log('headers: ' + this.httpOptions.headers.get('Authorization'));*/

    const params = new HttpParams().set('authToken', authToken);

    return this.http.get<Video[]>(this.baseUrl + '/library/' + serverIp + '/onDeck', {params})
      .pipe(
        map((response: any) => {

          this.alertify.message('Videos loaded!');
          console.log("retrieved videos");

          return response;
        })
      );
  }
}
