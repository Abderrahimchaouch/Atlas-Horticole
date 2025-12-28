import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router'; // Ajouter cette importation
import { Product } from '../../../../../core/models/products.model';

@Component({
  selector: 'app-products-pres',
  standalone: false,
  templateUrl: './products-pres.component.html',
  styleUrl: './products-pres.component.scss'
})
export class ProductsPresComponent {
  @Input() products: Product[] = [];
  @Input() filteredProducts: Product[] = [];
  @Input() categories: string[] = [];
  @Input() selectedCategory: string = 'Tous';
  @Input() searchQuery: string = '';
  @Input() inStockOnly: boolean = false;

  @Output() categoryChange = new EventEmitter<string>();
  @Output() searchChange = new EventEmitter<string>();
  @Output() stockFilterChange = new EventEmitter<boolean>();
  @Output() productDetails = new EventEmitter<string>();
  @Output() requestQuote = new EventEmitter<void>();

  constructor(private router: Router) { }

  onCategoryChange(category: string): void {
    this.categoryChange.emit(category);
  }

  onSearchChange(query: string): void {
    this.searchChange.emit(query);
  }

  onStockFilterChange(checked: boolean): void {
    this.stockFilterChange.emit(checked);
  }

  onProductDetails(productId: string): void {
    this.productDetails.emit(productId);
  }
  onRequestQuote(): void {
    this.requestQuote.emit();
  }
}