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
import {Directory} from "../models/directory.model";

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

  retrieveLibrarySections(serverIp: string) : Observable<Directory[]> {

    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

    const params = new HttpParams().set('authToken', authToken);

    return this.http.get<Directory[]>(this.baseUrl + '/library/' + serverIp + '/sections', {params})
      .pipe(
        map((response: any) => {

          this.alertify.message('Retrieved all sections!');
          console.log("retrieved sections");

          return response;
        })
      );
  }

  retrieveLibrarySectionBySectionKey(serverIp: string, sectionKey: string) : Observable<Directory[]> {

    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

    const params = new HttpParams().set('authToken', authToken);

    return this.http.get<Directory[]>(this.baseUrl + '/library/' + serverIp + '/sections/' + sectionKey, {params})
      .pipe(
        map((response: Directory[]) => {

          this.alertify.message('Retrieved Section by section key');
          console.log("retrieved sections");

          let directories: Directory[] = [];

          response.forEach(directory => {
            /*if (directory.secondary === null && directory.key != 'folder' && directory) {
              /!*console.log('Primary Directory: ' + directory.title);
              console.log('Primary Directory secondary: ' + directory.secondary);*!/
              directories.push(directory);
            }*/
            switch (directory.key) {
              case 'recentlyAdded':
              case 'onDeck':
              case 'newest':
                directories.push(directory);
                break;
              default:
                break;
            }
          });

          return directories;
        })
      );
  }

  retrieveLibrarySectionBySectionKeyAndDirectoryKey(serverIp: string, sectionKey: string, directoryKey: string) : Observable<Video[]> {

    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

    const params = new HttpParams().set('authToken', authToken);

    console.log("test section key: " + sectionKey);

    return this.http.get<Video[]>(this.baseUrl + '/library/' + serverIp + '/sections/' + sectionKey + '/directory/' + directoryKey, {params})
      .pipe(
        map((response: any) => {

          this.alertify.message('Retrieved section by section key and directory key!');
          console.log("retrieved sections");

          return response;
        })
      );
  }

  retrieveMediaDownloadLink(video: Video, serverIp: string): Observable<string> {

    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);

    let httpOptions = {
      params: new HttpParams().set('authToken', authToken),
      responseType: 'text' as 'json'
    };


    return this.http.post(this.baseUrl + '/library/' + serverIp + '/metadata', video, httpOptions)
      .pipe(
        map((response: any) => {

          let url: string = response;
          if (!url.includes('http://') && !url.includes('htts://')) {
            url = 'http://' + url;
          }

          //console.log("Download link retrieved: " + url);

          return url;
        })
      );
  }
}
