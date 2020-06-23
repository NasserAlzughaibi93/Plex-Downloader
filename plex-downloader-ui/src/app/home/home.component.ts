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
  styleUrls: ['./home.component.scss']
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
              private componentMessagingService: ComponentMessagingService) {
    this.firstTimeSetupCompleted = localStorage.getItem(Constants.FIRST_TIME_SETUP_COMPLETE) === 'true';
    if (!this.firstTimeSetupCompleted) {
      console.log("getting server lists.");
      this.libraryService.retrievePlexResources().subscribe((devices) => {
        // console.log('worked');
        this.devices = devices;
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
