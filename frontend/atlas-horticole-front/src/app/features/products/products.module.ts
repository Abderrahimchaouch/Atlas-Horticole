import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { ProductsContComponent } from './products-cont/products-cont.component';
import { ProductsPresComponent } from './products-pres/products-pres.component';


const routes: Routes = [
  {
    path: "", component: ProductsContComponent
  }
]
@NgModule({
  declarations: [
    ProductsContComponent,
    ProductsPresComponent],
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
