import {Deserializable} from "./deserializable.model";
import {Subscription} from "./user/subscription.model";
import {Roles} from "./user/roles.model";
import {ComponentAction, ComponentName} from "./component-name.model";

export class ComponentMessage implements Deserializable {

  toComponent: ComponentName;
  fromComponent: ComponentName;
  componentAction: ComponentAction;
  message: any;

  constructor(toComponent: ComponentName,
              fromComponent: ComponentName,
              componentAction: ComponentAction,
              message: any) {
  }


  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
