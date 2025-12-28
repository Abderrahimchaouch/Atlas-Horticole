import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HeroSlide } from '../heroSlide.model';

@Component({
  selector: 'app-hero-slide-cont',
  standalone: false,
  templateUrl: './hero-slide-cont.component.html',
  styleUrl: './hero-slide-cont.component.scss'
})
export class HeroSlideContComponent implements OnInit, OnDestroy {
  currentSlideIndex = 0;
  searchQuery = '';
  private slideInterval: any;
  private readonly SLIDE_DURATION = 6000; // 6 seconds

  slides: HeroSlide[] = [
    {
      id: '1',
      title: 'Solutions Agricoles Professionnelles',
      subtitle: 'Inspirées par la force des montagnes de l\'Atlas',
      description: 'Découvrez notre gamme complète de produits pour optimiser vos cultures',
      imageUrl: 'images/slide1.png',
      ctaText: 'Découvrir nos produits',
      ctaRoute: '/produits'
    },
    {
      id: '2',
      title: 'Biostimulants & Correcteurs',
      subtitle: 'La nutrition optimale pour vos cultures',
      description: 'Des solutions innovantes pour améliorer la santé et le rendement de vos plantes',
      imageUrl: 'images/biostimulant.png',
      ctaText: 'Voir les solutions',
      ctaRoute: '/produits'
    },
    {
      id: '3',
      title: 'Lutte Intégrée',
      subtitle: 'Protection naturelle des cultures',
      description: 'Biocontrôle et pollinisation pour une agriculture durable',
      imageUrl: 'images/coccinelle-pucerons.jpg',
      ctaText: 'En savoir plus',
      ctaRoute: '/produits'
    }
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.startSlideshow();
  }

  ngOnDestroy(): void {
    this.stopSlideshow();
  }

  private startSlideshow(): void {
    this.slideInterval = setInterval(() => {
      this.currentSlideIndex = (this.currentSlideIndex + 1) % this.slides.length;
    }, this.SLIDE_DURATION);
  }

  private stopSlideshow(): void {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  }

  handleSlideChange(index: number): void {
    this.currentSlideIndex = index;
    this.stopSlideshow();
    this.startSlideshow();
  }

  handleSearchQueryChange(query: string): void {
    this.searchQuery = query;
  }

  handleSearchSubmit(query: string): void {
    this.router.navigate(['/recherche'], { queryParams: { q: query } });
  }

}
