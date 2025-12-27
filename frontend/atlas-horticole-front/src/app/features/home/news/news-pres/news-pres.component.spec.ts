import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsPresComponent } from './news-pres.component';

describe('NewsPresComponent', () => {
  let component: NewsPresComponent;
  let fixture: ComponentFixture<NewsPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
