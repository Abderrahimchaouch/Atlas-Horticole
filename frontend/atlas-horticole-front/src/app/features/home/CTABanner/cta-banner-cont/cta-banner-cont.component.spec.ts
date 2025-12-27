import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CtaBannerContComponent } from './cta-banner-cont.component';

describe('CtaBannerContComponent', () => {
  let component: CtaBannerContComponent;
  let fixture: ComponentFixture<CtaBannerContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CtaBannerContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CtaBannerContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
