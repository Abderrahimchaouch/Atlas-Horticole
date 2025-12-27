import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderPresComponent } from './header-pres.component';

describe('HeaderPresComponent', () => {
  let component: HeaderPresComponent;
  let fixture: ComponentFixture<HeaderPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
