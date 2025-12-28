import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { ContactContComponent } from './contact-cont/contact-cont.component';
import { ContactPresComponent } from './contact-pres/contact-pres.component';

const routes: Routes = [
  {
    path: "", component: ContactContComponent
  }
]


@NgModule({
  declarations: [
    ContactContComponent,
    ContactPresComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule
  ]
  ,
  exports: [
    ContactContComponent
  ]
})
export class ContactModule { }
