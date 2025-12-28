import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ActualitesContComponent } from './actualites-cont/actualites-cont.component';
import { ActualitesPresComponent } from './actualites-pres/actualites-pres.component';
import { ArticleDetailsContComponent } from './acticleDetails/article-details-cont/article-details-cont.component';
import { ArticleDetailsPresComponent } from './acticleDetails/article-details-pres/article-details-pres.component';


const routes: Routes = [
  {
    path: "", component: ActualitesContComponent
  },
  {
    path: ":id", component: ArticleDetailsContComponent
  }
];

@NgModule({
  declarations: [
    ActualitesContComponent,
    ActualitesPresComponent,
    ArticleDetailsContComponent,
    ArticleDetailsPresComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    ActualitesContComponent
  ]
})
export class ArticlesModule { }