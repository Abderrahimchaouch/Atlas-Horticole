import { Component, Input } from '@angular/core';
import { ProductCategory } from '../product-categories-cont/product-categories.model';

@Component({
  selector: 'app-product-categories-pres',
  standalone: false,
  templateUrl: './product-categories-pres.component.html',
  styleUrl: './product-categories-pres.component.scss'
})
export class ProductCategoriesPresComponent {

  @Input() categories: ProductCategory[] = [];
  @Input() sectionTitle: string = 'Nos Expertises';
  @Input() sectionSubtitle: string = 'Des solutions adaptées à tous vos besoins agricoles';
}
