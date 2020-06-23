import {Pipe, PipeTransform} from '@angular/core';
import {SearchCriteriaModel} from "../models/search-criteria.model";
import {Directory} from "../models/directory.model";

@Pipe({
  name: 'librarySeriesSearchCriteriaFilterPipe'
})
export class LibrarySeriesSearchCriteriaFilterPipe implements PipeTransform {

  transform(seriesList: Directory[], searchCriteria: SearchCriteriaModel): Directory[] {

    if (!seriesList || !searchCriteria ) {
      return seriesList;
    }

    if (!searchCriteria.rating && !searchCriteria.year && !searchCriteria.genreTag) {
      return seriesList;
    }

    console.log('Search criteria year: ' + searchCriteria.year);

    return seriesList.filter(show => {
      console.log('video name: ' + show.title);

      if (searchCriteria.year != null && searchCriteria.year === show.year) {
        return show;
      }

      if (searchCriteria.rating != null && show.rating != null &&
        searchCriteria.rating >= show.rating) {

        return show
      }

      if (!show.genre) {
        return;
      }

      for (let aGenre of show.genre) {
        console.log('the genre' + aGenre);
        if (aGenre.tag === searchCriteria.genreTag) {
          return show;
        }
      }

    });
  }
}
