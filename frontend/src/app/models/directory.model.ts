import {Location} from "./location.model";


export interface Directory {
  agent?:            string;
  allowSync?:        string;
  art?:              string;
  composite?:        string;
  content?:          string;
  contentChangedAt?: string;
  createdAt?:        string;
  directory?:        string;
  directoryId?:      number;
  filters?:          string;
  key?:              string;
  language?:         string;
  location?:         Location;
  refreshing?:       string;
  scannedAt?:        string;
  scanner?:          string;
  thumb?:            string;
  title?:            string;
  type?:             string;
  updatedAt?:        string;
  uuid?:             string;
}
