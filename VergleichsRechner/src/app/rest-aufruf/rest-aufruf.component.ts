import { Component, OnInit } from '@angular/core';
import {RestApiProvider} from '../providers/rest-api/rest-api';
import {Tarif} from '../model/tarif';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Message, SelectItem} from 'primeng/api';

@Component({
  selector: 'app-rest-aufruf',
  templateUrl: './rest-aufruf.component.html',
  styleUrls: ['./rest-aufruf.component.scss']
})
export class RestAufrufComponent implements OnInit {

  private tarife: Tarif[];
  private cols: any[];

  tariftypen: string[] = ['BASE', 'DIV', 'HAM', 'HMO' ];
  selectedTarif: string;
  msgs: Message[] = [];

  userform: FormGroup;

  submitted, mitUnfall, isBaseP, isBaseF: boolean;

  altersklassen, regionen,  franchisen: SelectItem[];

  constructor(public rest: RestApiProvider, private fb: FormBuilder) { }

  ngOnInit() {

    this.userform = this.fb.group({
      'altersklasse': new FormControl('', Validators.required),
      'region': new FormControl('', Validators.required),
      'mitUnfall': new FormControl('', Validators.nullValidator),
      'isBaseP': new FormControl('', Validators.nullValidator),
      'isBaseF': new FormControl('', Validators.nullValidator),
      'tariftyp': new FormControl('', Validators.required),
      'franchise': new FormControl('', Validators.required),
      'kanton': new FormControl('', Validators.compose([Validators.required, Validators.minLength(2), Validators.maxLength(2)])),
    });

    this.altersklassen = [];
    this.altersklassen.push({label:'Bitte auswählen', value:''});
    this.altersklassen.push({label:'Kind', value:'KIN'});
    this.altersklassen.push({label:'Jugendliche(r)', value:'JUG'});
    this.altersklassen.push({label:'Erwachsene(r)', value:'ERW'});

    this.regionen = [];
    this.regionen.push({label:'Bitte auswählen', value:''});
    this.regionen.push({label:'Region 0', value:'0'});
    this.regionen.push({label:'Region 1', value:'1'});
    this.regionen.push({label:'Region 2', value:'2'});
    this.regionen.push({label:'Region 3', value:'3'});


    this.franchisen = [];
    this.franchisen.push({label:'Bitte auswählen', value:''});
    this.franchisen.push({label:'0', value:'0'});
    this.franchisen.push({label:'100', value:'100'});
    this.franchisen.push({label:'200', value:'200'});
    this.franchisen.push({label:'300', value:'300'});
    this.franchisen.push({label:'400', value:'400'});
    this.franchisen.push({label:'500', value:'500'});
    this.franchisen.push({label:'600', value:'600'});
    this.franchisen.push({label:'1000', value:'1000'});
    this.franchisen.push({label:'1500', value:'1500'});
    this.franchisen.push({label:'2000', value:'2000'});
    this.franchisen.push({label:'2500', value:'2500'});


    this.cols = [
      { field: 'Tarifbezeichnung', header: 'Tarifbezeichnung' },
      { field: 'Tarif', header: 'Tarif' },
      { field: 'Altersuntergruppe', header: 'Altersuntergruppe' },
      { field: 'Prämie', header: 'Prämie' },
    ];

  }

  onSubmit(value: string) {
    this.submitted = true;
    this.msgs = [];
    //this.msgs.push({severity:'info', summary:'Success', detail:'Form Submitted'});

    this.rest.findTarife(
      this.selectedTarif
      , this.userform.value['kanton']
      , this.userform.value['region']
      , this.userform.value['altersklasse']
      , this.userform.value['franchise']
      , this.mitUnfall, this.isBaseP, this.isBaseF
    ).subscribe(
      v => this.tarife = v,
      error => { throw Error(error); },
      () => {
      }
    );


  }

  get diagnostic() {
    let url: string;
    url = `http://localhost:8080/tarife/${this.selectedTarif}/${this.userform.value['kanton']}/${this.userform.value['region']}/${this.userform.value['altersklasse']}/${this.userform.value['franchise']}`;
    url += '?unfalleinschluss=' + (this.mitUnfall ? '1' : '0');
    url += '&baseTarif=' + (this.isBaseP ? '1' : '0');
    url += '&baseFranchise=' + (this.isBaseF ? '1' : '0');

    //return JSON.stringify(this.userform.value);
    return url;
  }
}
