import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CtaBannerPresComponent } from './cta-banner-pres.component';

describe('CtaBannerPresComponent', () => {
  let component: CtaBannerPresComponent;
  let fixture: ComponentFixture<CtaBannerPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CtaBannerPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CtaBannerPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
