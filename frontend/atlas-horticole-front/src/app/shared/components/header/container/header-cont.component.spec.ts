import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterContComponent } from './header-cont.component';

describe('FooterContComponent', () => {
  let component: FooterContComponent;
  let fixture: ComponentFixture<FooterContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FooterContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FooterContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
