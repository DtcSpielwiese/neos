import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { RestAufrufComponent } from './rest-aufruf/rest-aufruf.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {PrimengModule} from "./primeng.module";
import {AppRoutingModule} from "./app-routing.module";
import {RestApiProvider} from "./providers/rest-api/rest-api";

@NgModule({
  declarations: [
    AppComponent,
    RestAufrufComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    PrimengModule,
    AppRoutingModule
  ],
  providers: [RestApiProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
