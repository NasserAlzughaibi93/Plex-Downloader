import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {LibraryService} from "../_service/library.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Constants} from "../util/constants";
import {LibraryFilterPipe} from "./library-filter.pipe";
import {LibrarySeriesFilterPipe} from "./library-series-filter.pipe";
import {Genre} from "../models/genre.model";
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";
import {SearchCriteriaModel} from "../models/search-criteria.model";
import {ignoreElements} from "rxjs/operators";

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit, OnDestroy {

  @Input() libraryKey: string;

  readonly MAX_ROWS = 4;

  videosList: Video[];
  // videosList = [];
  showsList: Directory[];//Series directory
  searchQuery: string;
  genreSelection: string;
  searchCriteria: SearchCriteriaModel;
  genres = new Set<string>();
  years = new Set<number>();
  ratings = new Set<number>();

  constructor(private libraryService: LibraryService,
              private route: ActivatedRoute,
              private router: Router) {


    this.router.routeReuseStrategy.shouldReuseRoute = function(){
      return false;
    };

    //Handle library change from navbar.
    const navigation = this.router.getCurrentNavigation();

    if (navigation != null) {
      let libraryKey = navigation.extras.state ? navigation.extras.state.libraryKey : null;
      if (libraryKey == null) {
        console.log('Library key is null, error!');
        return;
      }

      this.libraryKey = libraryKey;
      //this.retrieveLibrary();

    }

  }

  ngOnInit() {
    if (!this.videosList && !this.showsList) {
      this.retrieveLibrary();
    }
  }

  retrieveLibrary() {
    if (!this.libraryKey) {
      console.log('Error: Didnt get the library key properly.');
      this.libraryKey = sessionStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);
      console.log('The library Key: ' + this.libraryKey);
    } else {
      sessionStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_KEY, this.libraryKey);
    }

    this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(this.libraryKey, 'all').subscribe(mediaContainer => {

      let years = new Array<number>();
      let ratings = new Array<number>();
      let genreTags = new Array<string>();

      if (mediaContainer.video != null && mediaContainer.video.length != 0) {
        // this.videos = mediaContainer.video;
        let videos = mediaContainer.video;
        this.videosList = mediaContainer.video;

        //this.searchCriteria = {rating: null, genreTag: null, year: null};
        for (let video of videos) {
          years.push(video.year);
          ratings.push(video.rating);
          if (video.genres != null) {
            for (let genre of video.genres) {
              genreTags.push(genre.tag);
            }
          }
        }

      } else if (mediaContainer.directory != null && mediaContainer.directory.length != 0) {
        // this.shows = mediaContainer.directory;
        let shows = mediaContainer.directory;
        this.showsList = mediaContainer.directory;

        for (let show of shows) {
          years.push(show.year);
          ratings.push(show.rating);
          if (show.genre != null) {
            for (let genre of show.genre) {
              console.log('Genre: ' + genre);
              genreTags.push(genre.tag);
            }
          }
        }
      }

      this.sortFilterLists(genreTags, ratings, years);

    }, error => {

      console.log('Error with getting all libraries: ' + error);
    });
  }

  sortFilterLists(genreTags: Array<string>, ratings: Array<number>, years: Array<number>) {
    this.genres = new Set<string>(genreTags.sort());

    this.ratings = new Set<number>(ratings.sort());

    this.years = new Set<number>(years.sort().reverse());
  }

  genreFilter(genreQuery: string){
    console.log('testing 123');
    this.genreSelection = genreQuery;
    this.searchCriteria = {genreTag: genreQuery};
  }

  yearFilter(yearQuery: number) {
    this.searchCriteria = {year: yearQuery};
  }

  fillSearchCriteria(year: number, genreTag: string) {

    this.searchCriteria = {year: year, genreTag: genreTag};
  }

  clearFilters() {
    this.searchQuery = '';
    this.genreSelection = '';
    this.searchCriteria = null;
  }

  ngOnDestroy(): void {
    this.videosList = null;
    this.showsList = null;
  }

}
