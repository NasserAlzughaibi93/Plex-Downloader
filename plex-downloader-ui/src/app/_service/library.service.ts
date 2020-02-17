import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {AlertifyService} from './alertify.service';
import {map} from 'rxjs/operators';
import {Constants} from "../util/constants";
import {MediaContainer} from "../models/mediacontainer.model";
import {Observable} from "rxjs";
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  baseUrl = Constants.PLEX_DOWNLOADER_BASE_URL + '/library';

  constructor(private  http: HttpClient, private router: Router, private alertify: AlertifyService) { }

  retrievePlexResources() : Observable<MediaContainer> {

    return this.http.get<MediaContainer>(this.baseUrl)
      .pipe(
        map((response: any) => {

          // this.alertify.message('Works!');

          return response;
        })
      );
  }

  retrieveLibraryOnDeck() : Observable<Video[]> {

    return this.http.get<Video[]>(this.baseUrl + '/{{serverIp}}/onDeck')
      .pipe(
        map((response: any) => {

          // this.alertify.message('Videos loaded!');
          console.log("retrieved videos");

          return response;
        })
      );
  }

  retrieveLibrarySections() : Observable<Directory[]> {

    return this.http.get<Directory[]>(this.baseUrl + '/{{serverIp}}/sections')
      .pipe(
        map((response: any) => {

          // this.alertify.message('Retrieved all sections!');
          console.log("retrieved sections");

          return response;
        })
      );
  }

  retrieveLibrarySectionBySectionKey(sectionKey: string) : Observable<Directory[]> {

    return this.http.get<Directory[]>(this.baseUrl + '/{{serverIp}}/sections/' + sectionKey)
      .pipe(
        map((response: Directory[]) => {

          // this.alertify.message('Retrieved Section by section key');
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

  retrieveLibrarySectionBySectionKeyAndDirectoryKey(sectionKey: string, directoryKey: string) : Observable<MediaContainer> {

    console.log("test section key: " + sectionKey);

    return this.http.get<MediaContainer>(this.baseUrl + '/{{serverIp}}/sections/' + sectionKey + '/directory/' + directoryKey)
      .pipe(
        map((response: any) => {

          // this.alertify.message('Retrieved section by section key and directory key!');
          console.log("retrieved sections");

          return response;
        })
      );
  }

  retrieveMediaDownloadLink(video: Video): Observable<string> {



    let httpOptions = {
      responseType: 'text' as 'json'
    };


    return this.http.post(this.baseUrl + '/{{serverIp}}/metadata', video, httpOptions)
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

  retrieveSearchResults(searchQuery: string) : Observable<MediaContainer> {

    return this.http.get<MediaContainer>(this.baseUrl + '/{{serverIp}}/search/' + searchQuery)
      .pipe(
        map((response: any) => {

          // this.alertify.message('Search results retrieved!');
          console.log('Search results retrieved');

          return response;
        })
      );
  }

  retrieveMediaMetaData(libraryKey: string) : Observable<Video[]>{

    const params = new HttpParams().set('libraryKey', libraryKey);

    return this.http.get<Video[]>(this.baseUrl + '/{{serverIp}}/metadata', {params})
      .pipe(
        map((videos: any) => {

          if (videos) {
            console.log("Retrieved video metadata!");
          } else {
            console.log("Video metadata is null");
          }

          return videos;
        })
      );
  }

  retrieveMediaMetaDataChildren(libraryKey: string) : Observable<Directory[]>{

    const params = new HttpParams().set('libraryKey', libraryKey);

    return this.http.get<Directory[]>(this.baseUrl + '/{{serverIp}}/metadata_children', {params})
      .pipe(
        map((directories: any) => {

          if (directories) {
            console.log("Retrieved video metadata!");
          } else {
            console.log("Video metadata is null");
          }

          return directories;
        })
      );
  }

  retrievePhotoFromPlexServer(metadataKey: string) : Observable<string> {

    let httpOptions = {
      responseType: 'text' as 'json',
      params: new HttpParams().set('metadataKey', metadataKey)
    };

    return this.http.get<string>(this.baseUrl + '/{{serverIp}}/metadata/photo', httpOptions)
      .pipe(
        map((photoUrl: any) => {

          if (photoUrl) {
            console.log("Retrieved transcoded photo!");
          } else {
            console.log("Photo is null!");
          }

          return photoUrl;
        })
      );
  }
}
