export interface Stream {
  _default?:          number;
  bitDepth?:          number;
  bitrate?:           number;
  chromaLocation?:    string;
  chromaSubsampling?: string;
  codec?:             string;
  displayTitle?:      string;
  frameRate?:         number;
  hasScalingMatrix?:  number;
  height?:            number;
  id?:                number;
  index?:             number;
  level?:             number;
  profile?:           string;
  refFrames?:         number;
  scanType?:          string;
  streamType?:        number;
  width?:             number;
}
