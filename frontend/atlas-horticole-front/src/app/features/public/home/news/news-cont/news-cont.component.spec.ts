import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsContComponent } from './news-cont.component';

describe('NewsContComponent', () => {
  let component: NewsContComponent;
  let fixture: ComponentFixture<NewsContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
