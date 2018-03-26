import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestAufrufComponent } from './rest-aufruf.component';

describe('RestAufrufComponent', () => {
  let component: RestAufrufComponent;
  let fixture: ComponentFixture<RestAufrufComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestAufrufComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RestAufrufComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
