import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable} from "rxjs/Observable";
import {map, catchError} from "rxjs/operators";
import {environment} from "../../../environments/environment";

/*
  Generated class for the RestApiProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class RestApiProvider {

  private apiUrl = 'http://localhost:8080/tarife/HMO/AG/0/KIN/0';

  constructor(public http: HttpClient) {}

  findTarife(tariftyp: string, kanton: string, region: string, altersklasse: string, franchise: string, mitUnfall: boolean, isBaseP: boolean, isBaseF: boolean) : Observable<any[]> {


    let url: string;
    url = `${environment.NEO_SERVICES_HOST}/tarife/${tariftyp}/${kanton}/${region}/${altersklasse}/${franchise}`;
    url += '?unfalleinschluss=' + (mitUnfall ? '1' : '0');
    url += '&baseTarif=' + (isBaseP ? '1' : '0');
    url += '&baseFranchise=' + (isBaseF ? '1' : '0');

    const tarife: Observable<any[]> = this.http.get(url).pipe(
      map(this.extractData),
      catchError(this.handleError)
    );

    return tarife;
  }


  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  private handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const err = error || '';
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }

}
