import { Pipe, PipeTransform } from '@angular/core';
import {Video} from "../models/video.model";
import {SearchCriteriaModel} from "../models/search-criteria.model";

@Pipe({
  name: 'librarySearchCriteriaFilterPipe'
})
export class LibrarySearchCriteriaFilterPipe implements PipeTransform {

  transform(videosList: Video[], searchCriteria: SearchCriteriaModel): Video[] {

    if (!videosList || !searchCriteria ) {
      return videosList;
    }

    if (!searchCriteria.rating && !searchCriteria.year && !searchCriteria.genreTag) {
      return videosList;
    }

    console.log('Search criteria year: ' + searchCriteria.year);

    return videosList.filter(video => {
      console.log('video name: ' + video.title);

      if (searchCriteria.year != null && searchCriteria.year === video.year) {
        return video;
      }

      if (searchCriteria.rating != null && video.rating != null &&
        searchCriteria.rating >= video.rating) {

        return video
      }

      if (!video.genres) {
        return;
      }

      for (let aGenre of video.genres) {
        console.log('the genre' + aGenre);
        if (aGenre.tag === searchCriteria.genreTag) {
          return video;
        }
      }

    });
  }
}
