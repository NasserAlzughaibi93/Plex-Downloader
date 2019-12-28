import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {Device} from "../models/device.model";
import {NavbarComponent} from "../navbar/navbar.component";
import {Video} from "../models/video.model";
import {Connection} from "../models/connection.model";
import {Constants} from "../util/constants";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  devices: Device[];

  videos: Video[];

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService) {
  }

  ngOnInit() {
  }

  retrieveOnDeckLibrary() {
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    let selected = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
    let serverIp: string;


    this.devices.forEach(device => {

      /*console.log('selected name: ' + selected);
      console.log('device name: ' + device.name);*/

      if (selected === device.name) {
        if (device.connection.length > 0) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              serverIp = connection.address + ':' + connection.port
            }
          });

          console.log("the ip address: " + serverIp);

          this.libraryService.retrieveLibraryOnDeck(serverIp).subscribe((videos) => {
            console.log('got on deck videos');
            this.videos = videos;
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

  resolvePosterURL(video: Video): string {
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI) + video.parentThumb + authTokenHeader;
    console.log("calling: " + url);
    return url;
  }

}
