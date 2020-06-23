import {LoggingLevel} from "./logging.level.model";

export interface SystemSettingsDTO {
  loggingLevel?: LoggingLevel,
  maxDownloadsPerUser?: bigint,
  downloadIntervalInMinutes?: bigint
}
