import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileCardComponent } from './mobile-card.component';

describe('MobileCardComponent', () => {
  let component: MobileCardComponent;
  let fixture: ComponentFixture<MobileCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MobileCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MobileCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
