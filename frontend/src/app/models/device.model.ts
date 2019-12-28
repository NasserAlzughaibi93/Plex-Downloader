import {Connection} from "./connection.model";

export interface Device {
  accessToken?:            string;
  clientIdentifier?:       string;
  connection?:             Connection[];
  createdAt?:              string;
  device?:                 string;
  deviceId?:               number;
  dnsRebindingProtection?: string;
  home?:                   string;
  httpsRequired?:          string;
  lastSeenAt?:             string;
  name?:                   string;
  natLoopbackSupported?:   string;
  owned?:                  string;
  ownerID?:                string;
  platform?:               string;
  platformVersion?:        string;
  presence?:               string;
  product?:                string;
  productVersion?:         string;
  provides?:               string;
  publicAddress?:          string;
  publicAddressMatches?:   string;
  relay?:                  string;
  sourceTitle?:            string;
  synced?:                 string;
}
