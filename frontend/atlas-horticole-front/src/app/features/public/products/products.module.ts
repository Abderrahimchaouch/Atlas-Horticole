import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductDetailsContComponent } from './product-Detail/product-details-cont/product-details-cont.component';
import { ProductDetailsPresComponent } from './product-Detail/product-details-pres/product-details-pres.component';
import { ProductsContComponent } from './product/products-cont/products-cont.component';
import { ProductsPresComponent } from './product/products-pres/products-pres.component';
import { SharedModule } from '../../../shared/shared.module';

const routes: Routes = [
  {
    path: "",
    component: ProductsContComponent
  },
  {
    path: ":id",
    component: ProductDetailsContComponent
  }
]

@NgModule({
  declarations: [
    ProductsContComponent,
    ProductsPresComponent,
    ProductDetailsContComponent,
    ProductDetailsPresComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ],
  exports: [
    ProductsContComponent
  ]
})
export class ProductsModule { }