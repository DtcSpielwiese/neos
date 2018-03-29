import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestAufrufComponent } from './rest-aufruf.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PrimengModule} from "../primeng.module";
import {RestApiProvider} from "../providers/rest-api/rest-api";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('RestAufrufComponent', () => {
  let component: RestAufrufComponent;
  let fixture: ComponentFixture<RestAufrufComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestAufrufComponent ],
      imports: [
        PrimengModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
      ],
      providers: [
        RestApiProvider,
      ],
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
