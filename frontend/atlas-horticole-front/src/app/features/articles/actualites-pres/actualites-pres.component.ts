import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NewsArticle } from '../articles.model';

@Component({
  selector: 'app-actualites-pres',
  standalone: false,
  templateUrl: './actualites-pres.component.html',
  styleUrl: './actualites-pres.component.scss'
})
export class ActualitesPresComponent {
  @Input() articles: NewsArticle[] = [];
  @Input() filteredArticles: NewsArticle[] = [];
  @Input() categories: string[] = [];
  @Input() selectedCategory: string = 'Tous';
  @Input() searchQuery: string = '';
  @Input() featuredArticle?: NewsArticle;

  @Output() categoryChange = new EventEmitter<string>();
  @Output() searchChange = new EventEmitter<string>();

  onCategoryChange(category: string): void {
    this.categoryChange.emit(category);
  }

  onSearchChange(query: string): void {
    this.searchChange.emit(query);
  }
}
