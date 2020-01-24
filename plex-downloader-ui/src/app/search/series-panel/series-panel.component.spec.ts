import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesPanelComponent } from './series-panel.component';

describe('SeriesPanelComponent', () => {
  let component: SeriesPanelComponent;
  let fixture: ComponentFixture<SeriesPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeriesPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
