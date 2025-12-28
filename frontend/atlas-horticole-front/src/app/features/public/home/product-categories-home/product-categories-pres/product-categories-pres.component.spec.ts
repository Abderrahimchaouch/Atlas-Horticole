import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCategoriesPresComponent } from './product-categories-pres.component';

describe('ProductCategoriesPresComponent', () => {
  let component: ProductCategoriesPresComponent;
  let fixture: ComponentFixture<ProductCategoriesPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductCategoriesPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductCategoriesPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
