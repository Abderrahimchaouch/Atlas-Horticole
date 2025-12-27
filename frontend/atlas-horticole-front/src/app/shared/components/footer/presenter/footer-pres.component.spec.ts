import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterPresComponent } from './footer-pres.component';

describe('FooterComponent', () => {
  let component: FooterPresComponent;
  let fixture: ComponentFixture<FooterPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FooterPresComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FooterPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
