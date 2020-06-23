import {LoggingLevel} from "./logging.level.model";
import {User} from "../user/user.model";

export interface SystemSettings {
  id?: bigint,
  plexUser?: User,
  loggingLevel?: LoggingLevel,
  maxDownloadsPerUser?: bigint,
  downloadIntervalInMinutes?: bigint
}
