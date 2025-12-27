import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutContComponent } from './about-cont/about-cont.component';
import { AboutPresComponent } from './about-pres/about-pres.component';

const routes: Routes = [
  {
    path: '', component: AboutContComponent
  }
]

@NgModule({
  declarations: [
    AboutContComponent,
    AboutPresComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ],
  exports: [
    AboutContComponent]
})
export class AboutModule { }
