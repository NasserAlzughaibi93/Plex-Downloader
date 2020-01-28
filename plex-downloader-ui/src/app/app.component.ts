import {Component, ElementRef} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'PlexDownloader';
  constructor(private elementRef: ElementRef){

  }

  ngAfterViewInit(){
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#333333';
    this.elementRef.nativeElement.ownerDocument.body.style.textDecorationColor = '#ffffff';
    this.elementRef.nativeElement.ownerDocument.head.style.color = '#ffffff';
  }
}
