import {Component, OnInit} from '@angular/core';
import {Device} from "../models/device.model";
import {LibraryService} from "../_service/library.service";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentMessage} from "../models/component-message.model";
import {ComponentAction, ComponentName} from "../models/component-name.model";
import {Directory} from "../models/directory.model";
import {Router} from "@angular/router";

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

  subscription: any;

  constructor(private router: Router,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) { }

  ngOnInit() {

    this.directories = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_LIBRARIES));
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    this.selectedServer = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
    let firstTimeSetupCompleted: boolean = localStorage.getItem(Constants.FIRST_TIME_SETUP_COMPLETE) === 'true';


    if ( firstTimeSetupCompleted && (this.devices == null || this.devices.length == 0)) {
      this.retrievePlexResources();
    } else if (this.devices == null || this.devices.length == 0) {
      this.retrievePlexResources();
    }else {
      this.filteredServer = this.devices.filter(t=>t.name == this.selectedServer);
    }

    this.subscription = this.componentMessagingService.getMessage()
      .subscribe(message => {
        console.log('Printing message from message service: ');
        console.log(message);
        console.log(message.componentAction);

        if (message.componentAction == ComponentAction.FIRST_TIME_SETUP) {
          this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
          this.selectedServer = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
        }
      });
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
  }

  searchQ(searchQuery: string) {
    console.log('Searching for query: ' + searchQuery);
    this.router.navigate(['/search', searchQuery])
  }
}
