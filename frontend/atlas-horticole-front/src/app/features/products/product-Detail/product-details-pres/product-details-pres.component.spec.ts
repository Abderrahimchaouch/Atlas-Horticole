import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDetailsPresComponent } from './product-details-pres.component';

describe('ProductDetailsPresComponent', () => {
  let component: ProductDetailsPresComponent;
  let fixture: ComponentFixture<ProductDetailsPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductDetailsPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductDetailsPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
