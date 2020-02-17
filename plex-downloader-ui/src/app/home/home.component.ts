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
import {LoadingScreenService} from "../_service/loading.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  firstTimeSetupCompleted = false;
  selectedLibrary: Directory;
  selectedServer: any;

  devices: Device[];
  plexLibraries: Directory[];

  sectionMap = new Map<Directory, Video>();
  sectionVideoMap = new Map<Directory, Video[]>();
  sectionVideoMapKeys: Directory[];

  //subscription: any;

  //tabSelected = new FormControl(0);

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService,
              private loadingScreenService: LoadingScreenService) {
    this.loadingScreenService.startLoading();
    this.firstTimeSetupCompleted = localStorage.getItem(Constants.FIRST_TIME_SETUP_COMPLETE) === 'true';
    if (!this.firstTimeSetupCompleted) {
      console.log("getting server lists.");
      this.libraryService.retrievePlexResources().subscribe((mediaContainer) => {
        // console.log('worked');
        this.devices = mediaContainer.device;
        localStorage.setItem(Constants.PLEX_SELECTED_SERVERS, JSON.stringify(this.devices));
        // console.log("devices size: " + this.devices.length);
      }, () => {
        console.log('Error');
      });
      //this.retrieveLibrarySectionBySectionKey();
    } else {
      //Maybe?
      //localStorage.clear();
      // this.retrieveLibrarySections();
      console.log('Moving to library view');
      this.router.navigate(['/library'])
    }

  }

  ngOnInit() {
  }

  retrieveLibrarySectionBySectionKey(libraryKey: string) {

    //Clear map for new section
    this.clearSectionMaps();

    this.libraryService.retrieveLibrarySectionBySectionKey(libraryKey).subscribe((directories) => {
      directories.forEach(directory => {
        console.log('By section key: ' + directory.title);
        this.sectionMap.set(directory, null);
      });

      this.retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey);

    });

  }

  retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey: string) {

    if (!this.sectionMap) {
      //TODO throw error
      console.log("ERROR: section issue");
    }

    console.log('Library key test: ' + libraryKey);

    for (let directory of this.sectionMap.keys()) {
      let directoryKey = directory.key;

      console.log('Directory key test: ' + directoryKey);


      this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey, directoryKey).subscribe((mediaContainer) => {

        if (mediaContainer.video != null && mediaContainer.video.length != 0) {
          let videos = mediaContainer.video;
          console.log('Amount of videos for section ' + directory.title + ": " + videos.length);
          this.sectionVideoMap.set(directory, videos);

          this.sectionVideoMapKeys = Array.from(this.sectionVideoMap.keys());
          // console.log("sectionVideoMapKeys: " + this.sectionVideoMapKeys.length);
        } else if (mediaContainer.directory != null && mediaContainer.directory.length != 0) {
          //TODO figure out displaying directory alongside videos in home page.
          //this.sectionVideoMap.set(mediaContainer.directory, null);
        }

      });
    }
  }

  retrieveLibrarySections() {

    this.libraryService.retrieveLibrarySections().subscribe((directories) => {
      this.plexLibraries = directories;
      localStorage.setItem(Constants.PLEX_SELECTED_LIBRARIES, JSON.stringify(this.plexLibraries));

      //console.log(JSON.stringify(this.plexLibraries));
    });
  }

  onLibrarySelect(library: Directory) {
    if (!library) {
      console.log('Error: selected library cannot be null');
    }

    localStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_NAME, library.title);

    console.log('About to get library: ' + library.title);
    this.selectedLibrary = library;
    this.firstTimeSetupCompleted = true;
    localStorage.setItem(Constants.FIRST_TIME_SETUP_COMPLETE, String(this.firstTimeSetupCompleted));
    this.router.navigateByUrl('/library', { state: { libraryKey: library.key } });
    //this.router.navigateByUrl('/library', { state: { libraryKey: library.key } });
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
          this.retrieveLibrarySections();

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
