import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {Device} from "../models/device.model";
import {Video} from "../models/video.model";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentAction, ComponentName} from "../models/component-name.model";
import {Directory} from "../models/directory.model";
import {ComponentMessage} from "../models/component-message.model";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  firstTimeSetupCompleted = false;
  selectedServer: any;

  devices: Device[];
  plexLibraries: Directory[];

  sectionMap = new Map<Directory, Video>();
  sectionVideoMap = new Map<Directory, Video[]>();
  sectionVideoMapKeys: Directory[];

  subscription: any;

  tabSelected = new FormControl(0);

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) {
    this.firstTimeSetupCompleted = localStorage.getItem(Constants.FIRST_TIME_SETUP_COMPLETE) === 'true';
    if (this.firstTimeSetupCompleted) {
      let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
      this.libraryService.retrievePlexResources(authToken).subscribe((mediaContainer) => {
        // console.log('worked');
        this.devices = mediaContainer.device;
        localStorage.setItem(Constants.PLEX_SELECTED_SERVERS, JSON.stringify(this.devices));
        // console.log("devices size: " + this.devices.length);
      }, () => {
        console.log('Error');
      });
      this.retrieveLibrarySections();
      //this.retrieveLibrarySectionBySectionKey();
    } else {
      //Maybe?
      //localStorage.clear();
    }
    this.subscription = this.componentMessagingService.getMessage()
      .subscribe(message => {
        /*console.log('Printing message from message service: ');
        console.log(message);
        console.log(message.componentAction);

        //if (message.componentAction === ComponentAction.LOAD_LIBRARIES) {
        //this.retrieveOnDeckLibrary();
        this.retrieveLibrarySectionBySectionKey();
        //clear main page lists when changing library section.
        this.sectionVideoMapKeys = new Array<Directory>();
        this.sectionVideoMap.clear();
        this.sectionMap.clear();
        this.onDeckVideos = new Array<Video>();*/
        // }
      });

    this.tabSelected.valueChanges.subscribe(index => {
      let libraryKey = this.plexLibraries[index].key;
      this.retrieveLibrarySectionBySectionKey(libraryKey);
    });
  }

  ngOnInit() {
  }

  /*retrieveOnDeckLibrary() {
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

  }*/

  retrieveLibrarySectionBySectionKey(libraryKey: string) {

    //Clear map for new section
    this.clearSectionMaps();

    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    //let sectionKey = localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);

    console.log("getting library by section key. IP: " + serverIp + "; Library Key: " + libraryKey);

    this.libraryService.retrieveLibrarySectionBySectionKey(serverIp, libraryKey).subscribe((directories) => {
      directories.forEach(directory => {
        console.log('By section key: ' + directory.title);
        this.sectionMap.set(directory, null);
        this.retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey);
      })
    });
  }

  retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey: string) {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    //let sectionKey = localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);

    if (!this.sectionMap) {
      //TODO throw error
      console.log("ERROR: section issue");
    }

    for (let directory of this.sectionMap.keys()) {
      let directoryKey = directory.key;

      this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(serverIp, libraryKey, directoryKey).subscribe((videos) => {
        console.log('Amount of videos for section ' + directory.title + ": " + videos.length);
        this.sectionVideoMap.set(directory, videos);


        this.sectionVideoMapKeys = Array.from(this.sectionVideoMap.keys());
        // console.log("sectionVideoMapKeys: " + this.sectionVideoMapKeys.length);

      });
    }
  }

  retrieveLibrarySections() {

    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);

    this.libraryService.retrieveLibrarySections(serverIp).subscribe((directories) => {
      this.plexLibraries = directories;
      localStorage.setItem(Constants.PLEX_SELECTED_LIBRARIES, JSON.stringify(this.plexLibraries));

      //console.log(JSON.stringify(this.plexLibraries));
    });
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

  //First time setup:
  onServerSelect() {
    console.log('server selected: ' + this.selectedServer);

    localStorage.setItem(Constants.PLEX_SELECTED_SERVER_NAME, this.selectedServer);

    this.devices.forEach(device => {
      if (this.selectedServer == device.name) {
        if (device.connection.length > 0 ) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              localStorage.setItem(Constants.PLEX_SELECTED_SERVER_URI, connection.address + ':' + connection.port);
              localStorage.setItem(Constants.PLEX_SELECTED_SERVER_FULL_URI, 'http://' + connection.address + ':' + connection.port);
            }
          });

          let message = new ComponentMessage(ComponentName.HOME, ComponentName.NAVBAR, ComponentAction.FIRST_TIME_SETUP, '');

          this.componentMessagingService.updateMessage(message);
          this.firstTimeSetupCompleted = true;
          localStorage.setItem(Constants.FIRST_TIME_SETUP_COMPLETE, String(this.firstTimeSetupCompleted));

        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }
      }

    });
  }


  clearSectionMaps() {
    this.sectionMap.clear();
    this.sectionVideoMap.clear();
    this.sectionVideoMapKeys = new Array<Directory>();

  }

}
