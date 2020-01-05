import {Component, OnInit} from '@angular/core';
import {Device} from "../models/device.model";
import {LibraryService} from "../_service/library.service";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentMessage} from "../models/component-message.model";
import {ComponentAction, ComponentName} from "../models/component-name.model";
import {Directory} from "../models/directory.model";
import {Video} from "../models/video.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  user: any = {};
  devices: Device[];
  directories: Directory[];
  selectedServer: any;
  filteredServer:any;
  selectedLibrary: any;
  filteredLibrary:any;

  constructor(private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) { }

  ngOnInit() {
    this.user = localStorage.getItem('user');
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    this.selectedServer = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
    this.directories = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_LIBRARIES));
    this.selectedLibrary = localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_NAME);

    if (this.devices == null || this.devices.length == 0) {
      this.retrievePlexResources();

    } else {
      this.filteredServer = this.devices.filter(t=>t.name == this.selectedServer);
      this.filteredLibrary = this.directories.filter(t=>t.title == this.selectedLibrary);
      let message = new ComponentMessage();
      message.fromComponent = ComponentName.NAVBAR;
      message.toComponent = ComponentName.HOME;
      message.componentAction = ComponentAction.LOAD_LIBRARIES;
      message.message = "Hello there!";

      this.componentMessagingService.updateMessage(message);
    }
  }

  retrievePlexResources() {

    const authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    this.libraryService.retrievePlexResources(authToken).subscribe((mediaContainer) => {
      // console.log('worked');
      this.devices = mediaContainer.device;
      localStorage.setItem(Constants.PLEX_SELECTED_SERVERS, JSON.stringify(this.devices));
      // console.log("devices size: " + this.devices.length);
    }, () => {
      console.log('Error');
    });
  }

  retrieveLibrarySections() {

    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);

    this.libraryService.retrieveLibrarySections(serverIp).subscribe((directories) => {
      this.directories = directories;
      localStorage.setItem(Constants.PLEX_SELECTED_LIBRARIES, JSON.stringify(this.directories));

    });
  }


  onServerSelect() {
    console.log('server selected: ' + this.selectedServer);
    this.filteredServer = this.devices.filter(t=>t.name == this.selectedServer);

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
        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }
      }
    });

    this.retrieveLibrarySections();

    //console.log(localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME));
  }

  onLibrarySelect() {
    console.log('library selected: ' + this.selectedLibrary);
    this.filteredLibrary = this.directories.filter(t=>t.title == this.selectedLibrary);

    localStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_NAME, this.selectedLibrary);

    this.directories.forEach(directory => {
      if (this.selectedLibrary == directory.title) {
        console.log('library name: ' + directory.title);
        localStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_KEY, directory.key);

        let message = new ComponentMessage();
        message.fromComponent = ComponentName.NAVBAR;
        message.toComponent = ComponentName.HOME;
        message.message = "Hello there!";

        this.componentMessagingService.updateMessage(message);
        /*if (device.connection.length > 0 ) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              localStorage.setItem(Constants.PLEX_SELECTED_SERVER_URI, 'http://' + connection.address + ':' + connection.port);
            }
          });
        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }*/
      }
    });

    //console.log(localStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_NAME));
  }

}
