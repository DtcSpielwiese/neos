import { NgModule } from '@angular/core';

import {TableModule} from 'primeng/table';
import {PanelModule} from 'primeng/panel';
import {CheckboxModule, DropdownModule, InputTextModule, RadioButtonModule} from 'primeng/primeng';
import {ButtonModule} from 'primeng/button';
import {MessageModule} from 'primeng/message';
import {MessagesModule} from 'primeng/messages';
import {GrowlModule} from 'primeng/growl';

@NgModule({
  imports: [TableModule, PanelModule, DropdownModule, InputTextModule, ButtonModule, MessagesModule, MessageModule, GrowlModule, CheckboxModule, RadioButtonModule],

  exports: [TableModule, PanelModule, DropdownModule, InputTextModule, ButtonModule, MessagesModule, MessageModule, GrowlModule, CheckboxModule, RadioButtonModule],
})


export class PrimengModule {
}
