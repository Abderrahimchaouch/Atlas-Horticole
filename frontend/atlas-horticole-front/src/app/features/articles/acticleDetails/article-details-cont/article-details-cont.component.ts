import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NewsArticle } from '../../articles.model';

@Component({
  selector: 'app-article-details-cont',
  standalone: false,
  templateUrl: './article-details-cont.component.html',
  styleUrl: './article-details-cont.component.scss'
})
export class ArticleDetailsContComponent implements OnInit {
  allArticles = [
    {
      id: '1',
      title: 'Lancement de la nouvelle gamme ORGANIC SOL',
      excerpt: 'Découvrez notre dernière innovation en matière d\'amendement de sol 100% végétal. Une formulation unique pour améliorer la fertilité de vos cultures.',
      content: `Atlas Horticole est fier d'annoncer le lancement de sa nouvelle gamme de biostimulants organiques ORGANIC SOL. Cette innovation majeure représente une avancée significative dans le domaine de l'agriculture durable.

## Une formulation 100% végétale unique

ORGANIC SOL est composé de matière organique 100% végétale, comprenant des acides organiques, des vitamines et des acides aminés. Son bas poids moléculaire favorise une assimilation optimale des nutriments par les plantes.

## Bénéfices pour vos cultures

- **Amélioration de la fertilité du sol** : Renforce les propriétés physicochimiques du sol
- **Stimulation de l'activité bactérienne** : Active la vie microbienne dans la zone racinaire
- **Effets systémiques** : Action antioxydante et résistance au stress
- **Optimisation du pH** : Facilite l'irrigation et le mélange fertilisant

## Composition détaillée

- Matière organique totale : 35%
- Carbone organique : 20.3%
- Azote total (N) : 2%
- Potassium (K₂O) : 3%
- pH : 3.5 à 6

## Mode d'application

Dosage recommandé : 2 applications par mois en injection - 5 L/ha

Cette nouvelle gamme s'inscrit dans notre engagement continu pour une agriculture respectueuse de l'environnement et économiquement performante.`,
      date: new Date('2024-12-20'),
      author: 'Équipe R&D Atlas Horticole',
      category: 'Innovation',
      tags: ['Biostimulants', 'Innovation', 'Agriculture durable'],
      readTime: 5,
      featured: true
    },
    {
      id: '2',
      title: 'Formation : Maîtrisez la lutte intégrée',
      excerpt: 'Participez à nos sessions de formation sur le biocontrôle et la pollinisation. Nos experts vous accompagnent pour optimiser vos pratiques agricoles.',
      content: `Atlas Horticole organise une série de formations techniques dédiées à la lutte intégrée et au biocontrôle. Ces sessions pratiques vous permettront de maîtriser les techniques les plus avancées en matière de protection biologique des cultures.

## Programme de formation

### Module 1 : Introduction à la lutte intégrée
- Principes fondamentaux du biocontrôle
- Identification des ravageurs et auxiliaires
- Stratégies de protection biologique

### Module 2 : Utilisation des auxiliaires
- ORIUS LAVIGATUS pour le contrôle des thrips
- NESIDIOCORIS TENUIS contre les aleurodes
- AMBLYSEIUS SWIRSKII pour la gestion des ravageurs

### Module 3 : Pollinisation naturelle
- Utilisation des ruches de bourdons
- Optimisation de la pollinisation
- Gestion des colonies

## Informations pratiques

- **Durée** : 2 jours (14 heures)
- **Lieu** : Centre de formation Atlas Horticole, Chtouka Ait Baha
- **Tarif** : Nous consulter
- **Prochaines sessions** : Janvier 2025

## Inscription

Pour vous inscrire ou obtenir plus d'informations, contactez-nous :
- Téléphone : 0665 63 07 27 / 0661 32 30 22
- Email : horticole.atlas@gmail.com`,
      date: new Date('2024-12-15'),
      author: 'Service Formation',
      category: 'Formation',
      tags: ['Formation', 'Lutte intégrée', 'Biocontrôle'],
      readTime: 4
    },
    // ... autres articles
  ];
  
  article?: NewsArticle;
  relatedArticles: NewsArticle[] = [];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    const articleId = this.route.snapshot.params['id'];
    this.loadArticle(articleId);
    this.loadRelatedArticles(articleId);
  }

  private loadArticle(articleId: string): void {
    // Simuler la récupération depuis une API


    this.article = this.allArticles.find(a => a.id === articleId);
  }

  private loadRelatedArticles(currentArticleId: string): void {
    // Simuler la récupération d'articles similaires


    const currentArticle = this.allArticles.find(a => a.id === currentArticleId);
    if (!currentArticle) return;

    this.relatedArticles = this.allArticles
      .filter(a =>
        a.id !== currentArticleId &&
        (a.category === currentArticle.category ||
          a.tags.some(tag => currentArticle.tags.includes(tag)))
      )
      .slice(0, 3);
  }

  shareArticle(platform: string): void {
    // Logique de partage
    console.log(`Partage sur ${platform}: ${this.article?.title}`);
  }

  printArticle(): void {
    window.print();
  }
}

