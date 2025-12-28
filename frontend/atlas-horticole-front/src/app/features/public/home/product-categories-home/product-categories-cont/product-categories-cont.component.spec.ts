import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCategoriesContComponent } from './product-categories-cont.component';

describe('ProductCategoriesContComponent', () => {
  let component: ProductCategoriesContComponent;
  let fixture: ComponentFixture<ProductCategoriesContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductCategoriesContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductCategoriesContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
