import { Component, OnInit } from '@angular/core';
import {RestApiProvider} from '../providers/rest-api/rest-api';
import {Tarif} from '../model/tarif';

@Component({
  selector: 'app-rest-aufruf',
  templateUrl: './rest-aufruf.component.html',
  styleUrls: ['./rest-aufruf.component.scss']
})
export class RestAufrufComponent implements OnInit {

  private tarife: Tarif[];
  private cols: any[];


  constructor(public rest: RestApiProvider) { }

  ngOnInit() {

    this.cols = [
      { field: 'Unfalleinschluss', header: 'Unfalleinschluss' },
      { field: 'Tarifbezeichnung', header: 'Tarifbezeichnung' },
      { field: 'Prämie', header: 'Prämie' },
    ];
    this.rest.getTarife().subscribe(
      v => this.tarife = v,
      error => { throw Error(error); },
      () => {
        }
    );
  }
}
