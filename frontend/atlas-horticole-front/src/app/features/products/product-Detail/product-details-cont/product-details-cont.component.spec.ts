import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDetailsContComponent } from './product-details-cont.component';

describe('ProductDetailsContComponent', () => {
  let component: ProductDetailsContComponent;
  let fixture: ComponentFixture<ProductDetailsContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductDetailsContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductDetailsContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
