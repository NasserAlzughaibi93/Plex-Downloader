import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {Device} from "../models/device.model";
import {Video} from "../models/video.model";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentAction} from "../models/component-name.model";
import {Directory} from "../models/directory.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  devices: Device[];

  onDeckVideos: Video[];

  sectionMap = new Map<Directory, Video>();
  sectionVideoMap = new Map<Directory, Video[]>();
  sectionVideoMapKeys: Directory[];

  subscription: any;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) {
    this.subscription = this.componentMessagingService.getMessage()
      .subscribe(message => {
        console.log('Printing message from message service: ');
        console.log(message);
        console.log(message.componentAction);

        //if (message.componentAction === ComponentAction.LOAD_LIBRARIES) {
        //this.retrieveOnDeckLibrary();
        this.retrieveLibrarySectionBySectionKey();
        //clear main page lists when changing library section.
        this.sectionVideoMapKeys = new Array<Directory>();
        this.sectionVideoMap.clear();
        this.sectionMap.clear();
        this.onDeckVideos = new Array<Video>();
        // }
      });
  }

  ngOnInit() {
  }

  retrieveOnDeckLibrary() {
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    let selected = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
    let serverIp: string;


    this.devices.forEach(device => {

      if (selected === device.name) {
        if (device.connection.length > 0) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              serverIp = connection.address + ':' + connection.port
            }
          });

          this.libraryService.retrieveLibraryOnDeck(serverIp).subscribe((videos) => {
            console.log('got on deck videos');
            this.onDeckVideos = videos;
          }, () => {
            console.log('Error');
          });
        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }
        return;
      }
    });

  }

  retrieveLibrarySectionBySectionKey() {

    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    let sectionKey = localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);

    console.log("getting library by section key");

    this.libraryService.retrieveLibrarySectionBySectionKey(serverIp, sectionKey).subscribe((directories) => {
      directories.forEach(directory => {
        console.log('By section key: ' + directory.title);
        this.sectionMap.set(directory, null);
        this.retrieveLibrarySectionBySectionKeyAndDirectoryKey();
      })
    });
  }

  retrieveLibrarySectionBySectionKeyAndDirectoryKey() {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    let sectionKey = localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);

    if (!this.sectionMap) {
      //TODO throw error
      console.log("ERROR: section issue");
    }

    for (let directory of this.sectionMap.keys()) {
      let directoryKey = directory.key;

      this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(serverIp, sectionKey, directoryKey).subscribe((videos) => {
        console.log('Amount of videos for section ' + directory.title + ": " + videos.length);
        this.sectionVideoMap.set(directory, videos);


        this.sectionVideoMapKeys = Array.from(this.sectionVideoMap.keys());
        // console.log("sectionVideoMapKeys: " + this.sectionVideoMapKeys.length);

      });
    }

  }

  resolvePosterURL(video: Video): string {
    //TODO have server side process URL example: http://{SERVER_IP}:{PORT}/photo/:/transcode?url=/library/metadata/13686/thumb/1576691662&width=500&height=500&X-Plex-Token={{APIKEY}}
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let thumb = video.type === 'movie' ? video.thumb : video.grandparentThumb;
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }

  startDownloadingMedia(video: Video) {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    if (serverIp === null || serverIp.trim().length === 0) {
      //TODO throw error
    }

    this.libraryService.retrieveMediaDownloadLink(video, serverIp).subscribe(downloadLink => {
      this.beginDownload(downloadLink);
    });

  }

  beginDownload(url: string) {
    window.open(url);
  }

}
