import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./features/public/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'contact',
    loadChildren: () => import('./features/public/contact/contact.module').then(m => m.ContactModule)
  },
  {
    path: 'a-propos',
    loadChildren: () => import('./features/public/about/about.module').then(m => m.AboutModule)
  },

  {
    path: 'produits',
    loadChildren: () => import('./features/public/products/products.module').then(m => m.ProductsModule)
  },
  {
    path: 'articles',
    loadChildren: () => import('./features/public/articles/articles.module').then(m => m.ArticlesModule)
  },
  {
    path: 'catalogue',
    loadChildren: () => import('./features/public/catalogue/catalogue.module').then(m => m.CatalogueModule)
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }