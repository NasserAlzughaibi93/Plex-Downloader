import {Component, OnInit} from '@angular/core';
import {Device} from "../models/device.model";
import {LibraryService} from "../_service/library.service";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentMessage} from "../models/component-message.model";
import {ComponentAction, ComponentName} from "../models/component-name.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  user: any = {};
  devices: Device[];
  selected: any;
  filtered :any;

  constructor(private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) { }

  ngOnInit() {
    this.user = localStorage.getItem('user');
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    this.selected = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);

    if (this.devices == null || this.devices.length == 0) {
      this.retrievePlexResources();

    } else {
      this.filtered = this.devices.filter(t=>t.name == this.selected);
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
      console.log('worked');
      this.devices = mediaContainer.device;
      localStorage.setItem(Constants.PLEX_SELECTED_SERVERS, JSON.stringify(this.devices));
      console.log("devices size: " + this.devices.length);
      let message = new ComponentMessage();
      message.fromComponent = ComponentName.NAVBAR;
      message.toComponent = ComponentName.HOME;
      message.message = "Hello there!";

      this.componentMessagingService.updateMessage(message);
    }, () => {
      console.log('Error');
    });
  }

  onOptionsSelected() {
    console.log('server selected: ' + this.selected);
    this.filtered = this.devices.filter(t=>t.name == this.selected);

    localStorage.setItem(Constants.PLEX_SELECTED_SERVER_NAME, this.selected);

    this.devices.forEach(device => {
      if (this.selected == device.name) {
        if (device.connection.length > 0 ) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              localStorage.setItem(Constants.PLEX_SELECTED_SERVER_URI, 'http://' + connection.address + ':' + connection.port);
            }
          });
        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }
      }
    });

    console.log(localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME));
  }

}
