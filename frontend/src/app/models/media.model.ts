import {Part} from "./part.model";

export interface Media {
  aspectRatio?:     number;
  audioChannels?:   number;
  audioCodec?:      string;
  bitrate?:         number;
  container?:       string;
  duration?:        number;
  height?:          number;
  id?:              number;
  part?:            Part;
  videoCodec?:      string;
  videoFrameRate?:  string;
  videoProfile?:    string;
  videoResolution?: number;
  width?:           number;
}
