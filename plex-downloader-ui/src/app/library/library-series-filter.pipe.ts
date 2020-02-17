import { Pipe, PipeTransform } from '@angular/core';
import {Directory} from "../models/directory.model";

@Pipe({
  name: 'librarySeriesFilter'
})
export class LibrarySeriesFilterPipe implements PipeTransform {


  transform(showList: Directory[], searchQuery: string): Directory[] {

    //console.log("Search term: " + searchQuery);

    if (!showList || !searchQuery) {
      return showList;
    }

    return showList.filter(show => show.title.toLowerCase().indexOf(searchQuery.toLowerCase()) !== -1);
  }
}
