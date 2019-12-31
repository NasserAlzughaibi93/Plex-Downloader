import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {ComponentMessage} from "../models/component-message.model";

@Injectable({
  providedIn: 'root'
})
export class ComponentMessagingService {

  private message = new Subject<ComponentMessage>();

  constructor() { }

  getMessage(): Observable<ComponentMessage> {
    return this.message.asObservable();
  }

  updateMessage(message: ComponentMessage) {
    this.message.next(message);
  }
}
