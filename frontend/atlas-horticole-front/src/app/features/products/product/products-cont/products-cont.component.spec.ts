import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsContComponent } from './products-cont.component';

describe('ProductsContComponent', () => {
  let component: ProductsContComponent;
  let fixture: ComponentFixture<ProductsContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductsContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
