import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import { ButtonsComponent } from './buttons/buttons.component';
import {primeNgModule} from "./primeng.module";

@NgModule({
  declarations: [
    AppComponent,
    ButtonsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    primeNgModule


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
