import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterContComponent } from './components/footer/container/footer-cont.component';
import { FooterPresComponent } from './components/footer/presenter/footer-pres.component';
import { HeaderContComponent } from './components/header/container/header-cont.component';
import { HeaderPresComponent } from './components/header/presenter/header-pres.component';



@NgModule({
  declarations: [
    FooterContComponent,
    FooterPresComponent,
    HeaderContComponent,
    HeaderPresComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule
  ]
  ,
  exports: [
    FooterContComponent,
    HeaderContComponent,
    HttpClientModule
  ]

})
export class SharedModule { }
