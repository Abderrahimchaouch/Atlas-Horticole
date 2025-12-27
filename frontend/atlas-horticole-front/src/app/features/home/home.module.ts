import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CtaBannerContComponent } from './CTABanner/cta-banner-cont/cta-banner-cont.component';
import { CtaBannerPresComponent } from './CTABanner/cta-banner-pres/cta-banner-pres.component';
import { HeroSlideContComponent } from './hero-slide/hero-slide-cont/hero-slide-cont.component';
import { HeroSlidePresComponent } from './hero-slide/hero-slide-pres/hero-slide-pres.component';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { NewsContComponent } from './news/news-cont/news-cont.component';
import { NewsPresComponent } from './news/news-pres/news-pres.component';
import { ProductCategoriesContComponent } from './product-categories-home/product-categories-cont/product-categories-cont.component';
import { ProductCategoriesPresComponent } from './product-categories-home/product-categories-pres/product-categories-pres.component';

@NgModule({
  declarations: [
    HomeComponent,
    HeroSlideContComponent,
    HeroSlidePresComponent,
    ProductCategoriesContComponent,
    ProductCategoriesPresComponent,
    CtaBannerContComponent,
    CtaBannerPresComponent,
    NewsContComponent,
    NewsPresComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ],
  exports: [
    HomeComponent,
    HeroSlideContComponent,
    ProductCategoriesContComponent,
    CtaBannerContComponent,
    NewsContComponent
  ]

})
export class HomeModule { }
