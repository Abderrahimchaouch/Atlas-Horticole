import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroSlidePresComponent } from './hero-slide-pres.component';

describe('HeroSlidePresComponent', () => {
  let component: HeroSlidePresComponent;
  let fixture: ComponentFixture<HeroSlidePresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroSlidePresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeroSlidePresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
