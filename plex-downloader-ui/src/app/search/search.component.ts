import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {Constants} from "../util/constants";
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";
import {SeriesPanelComponent} from "./series-panel/series-panel.component";
import {MatDialog} from "@angular/material/dialog";
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
              private componentMessagingService: ComponentMessagingService,
              public dialog: MatDialog) {

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

    this.libraryService.retrieveSearchResults(searchQuery).subscribe(mediaContainer => {

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

  openDialog(show: Directory): void {
    console.log("show example: " + show.title);
    const dialogRef = this.dialog.open(SeriesPanelComponent, {
      width: '60%',
      data: show
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }


}
