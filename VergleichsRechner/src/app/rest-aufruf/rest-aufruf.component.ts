import { Component, OnInit } from '@angular/core';
import {RestApiProvider} from "../providers/rest-api/rest-api";

@Component({
  selector: 'app-rest-aufruf',
  templateUrl: './rest-aufruf.component.html',
  styleUrls: ['./rest-aufruf.component.scss']
})
export class RestAufrufComponent implements OnInit {
  // pizzaSelection:string;
  restResponse: string;
;

  constructor(public rest: RestApiProvider) {  this.restResponse = 'initial Response from the Service';}

  ngOnInit() {
    this.getTarife();
  }
  getTarife() {
    this.rest.getTarife();
     /* .subscribe(
        v => this.getTarife = v,
        error =>  this.errorMessage = <any>error
        , () => {
          this.getTarife.sort((c1, c2) =>
          {
            console.log("sort....");
            if (c1.translations["de"] < c2.translations["de"]) return -1;
            else if (c1.translations["de"] > c2.translations["de"]) return 1;
            return 0;
          });
          this.countries_paging = this.countries.slice(this.current_page, this.paging)
        });*/
  }
}
