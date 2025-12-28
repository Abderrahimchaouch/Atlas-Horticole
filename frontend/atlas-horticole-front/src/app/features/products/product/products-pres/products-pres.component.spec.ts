import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsPresComponent } from './products-pres.component';

describe('ProductsPresComponent', () => {
  let component: ProductsPresComponent;
  let fixture: ComponentFixture<ProductsPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductsPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
