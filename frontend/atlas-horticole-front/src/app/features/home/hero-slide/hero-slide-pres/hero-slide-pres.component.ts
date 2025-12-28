import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HeroSlide } from '../hero-slide-cont/HeroSlide.interface';

@Component({
  selector: 'app-hero-slide-pres',
  standalone: false,
  templateUrl: './hero-slide-pres.component.html',
  styleUrl: './hero-slide-pres.component.scss',
  
})
export class HeroSlidePresComponent {
  @Input() slides: HeroSlide[] = [];
  @Input() currentSlideIndex: number = 0;
  @Input() searchQuery: string = '';

  @Output() slideChange = new EventEmitter<number>();
  @Output() searchQueryChange = new EventEmitter<string>();
  @Output() searchSubmit = new EventEmitter<string>();

  onSlideChange(index: number): void {
    this.slideChange.emit(index);
  }

  onSearchInput(value: string): void {
    this.searchQueryChange.emit(value);
  }

  onSearchSubmit(): void {
    if (this.searchQuery.trim()) {
      this.searchSubmit.emit(this.searchQuery.trim());
    }
  }

  get currentSlide(): HeroSlide {
    return this.slides[this.currentSlideIndex] || this.slides[0];
  }
}
