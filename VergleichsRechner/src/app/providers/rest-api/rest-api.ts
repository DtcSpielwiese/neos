import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable} from "rxjs/Observable";
import {map, catchError} from "rxjs/operators";
import {Tarif} from "../../model/tarif";

/*
  Generated class for the RestApiProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class RestApiProvider {

  private apiUrl = 'http://localhost:8080/tarife/HMO/AG/0/KIN/0';

  constructor(public http: HttpClient) {}

  getTarife() : Observable<any[]> {


    const tarife: Observable<any[]> = this.http.get(this.apiUrl).pipe(
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
