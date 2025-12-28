import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProductDetails } from '../../../../../core/models/product.details.model';

@Component({
  selector: 'app-product-details-pres',
  standalone: false,
  templateUrl: './product-details-pres.component.html',
  styleUrls: ['./product-details-pres.component.scss']
})
export class ProductDetailsPresComponent {
  @Input() product: ProductDetails | undefined;
  @Input() quantity: number = 1;
  @Input() isLoading?: boolean = false;
  @Input() error?: string | null = null;

  @Output() increaseQuantity = new EventEmitter<void>();
  @Output() decreaseQuantity = new EventEmitter<void>();
  @Output() requestQuote = new EventEmitter<void>();
  @Output() downloadDocumentation = new EventEmitter<void>();
  @Output() navigateToProduct = new EventEmitter<string>();
  @Output() goBack = new EventEmitter<void>();
}
