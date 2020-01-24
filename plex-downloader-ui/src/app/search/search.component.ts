import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {Constants} from "../util/constants";
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";
declare let $: any;

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  @ViewChild('collapse', null) collapse: ElementRef;

  searchComplete = false;

  movies: Video[] = new Array<Video>();
  shows: Directory[] = new Array<Directory>();
  episodes: Video[] = new Array<Video>();

  showIndex: number;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) {

    this.router.routeReuseStrategy.shouldReuseRoute = function(){
      return false;
    };

    const navigation = this.router.getCurrentNavigation();
    let query = navigation.extras.state ? navigation.extras.state.searchQuery : null;
    console.log('query is null: ' + query === null);
    if (query != null && query.trim().length != 0) {
      console.log('Searching for ' + query);

      this.retrieveSearchResults(query);

      //if (this.movies.length != 0 || this.shows.length != 0 || this.episodes.length != 0) {
      this.searchComplete = true;
      //}
    }
  }

  ngOnInit() {

    /*this.route.params.subscribe(params => {
      let query: string = params['searchQuery'];
      if (query != null && query.trim().length != 0) {
        console.log('Searching for ' + query);

        this.retrieveSearchResults(query);

        //if (this.movies.length != 0 || this.shows.length != 0 || this.episodes.length != 0) {
          this.searchComplete = true;
        //}
      }
    });*/
  }

  retrieveSearchResults(searchQuery: string) {

    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);

    this.libraryService.retrieveSearchResults(serverIp, searchQuery).subscribe(mediaContainer => {

      let videos = mediaContainer.video;
      let directories = mediaContainer.directory;

      console.log('searched videos size: ' + videos.length);
      console.log('searched directories size: ' + directories.length);

      videos.forEach(video => {
        if (video.type === 'movie') {
          this.movies.push(video);
        } else if (video.type === 'episode') {
          this.episodes.push(video);
        }
      });

      directories.forEach(directory => {
        if (directory.type === 'show') {
          this.shows.push(directory);
        }
      })

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

  resolveSeriesPosterURL(directory: Directory): string {
    //TODO have server side process URL example: http://{SERVER_IP}:{PORT}/photo/:/transcode?url=/library/metadata/13686/thumb/1576691662&width=500&height=500&X-Plex-Token={{APIKEY}}
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let thumb = directory.thumb;
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }

  updateShowIndex(index: number) {
    $(this.collapse.nativeElement).show('show-target' + index);
  }
}
