import { Component, OnInit } from '@angular/core';
import { NewsArticle } from '../../../../core/models/articles.model';

@Component({
  selector: 'app-actualites-cont',
  standalone: false,
  templateUrl: './actualites-cont.component.html',
  styleUrl: './actualites-cont.component.scss'
})
export class ActualitesContComponent implements OnInit {
  articles: NewsArticle[] = [];
  filteredArticles: NewsArticle[] = [];
  featuredArticle?: NewsArticle;
  categories: string[] = ['Tous', 'Innovation', 'Formation', 'Entreprise', 'Technique', 'Événements'];
  selectedCategory = 'Tous';
  searchQuery = '';

  ngOnInit(): void {
    this.loadArticles();
    this.applyFilters();
  }

  private loadArticles(): void {
    this.articles = [
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
      {
        id: '3',
        title: 'Atlas Horticole obtient la certification internationale',
        excerpt: 'Notre engagement pour la qualité reconnu au niveau international. Une nouvelle étape dans notre démarche d\'excellence et de développement durable.',
        content: `Nous sommes heureux d'annoncer qu'Atlas Horticole vient d'obtenir une certification internationale de qualité, témoignant de notre engagement continu pour l'excellence et la durabilité.

## Une reconnaissance internationale

Cette certification atteste de la conformité de nos processus de production et de nos produits aux standards internationaux les plus exigeants en matière de qualité et de sécurité.

## Nos engagements

### Qualité des produits
- Contrôle rigoureux à chaque étape de production
- Tests en laboratoire systématiques
- Traçabilité complète

### Développement durable
- Réduction de notre empreinte environnementale
- Utilisation de matières premières durables
- Économie circulaire

### Satisfaction client
- Support technique de qualité
- Conseil agronomique personnalisé
- Disponibilité et réactivité

## Impact pour nos clients

Cette certification vous garantit :
- Des produits de qualité constante
- Le respect des normes environnementales
- Une expertise reconnue internationalement

Merci à tous nos clients pour leur confiance qui nous motive à poursuivre nos efforts d'amélioration continue.`,
        date: new Date('2024-12-10'),
        author: 'Direction Atlas Horticole',
        category: 'Entreprise',
        tags: ['Certification', 'Qualité', 'Entreprise'],
        readTime: 3,
        featured: false
      },
      {
        id: '4',
        title: 'Correcteurs de carences : Guide d\'utilisation FERROLAS 4.9',
        excerpt: 'Tout ce que vous devez savoir sur l\'utilisation du FERROLAS 4.9 pour corriger efficacement la chlorose ferrique de vos cultures.',
        content: `Le FERROLAS 4.9 est notre solution phare pour la correction de la chlorose ferrique. Ce guide technique vous explique comment l'utiliser de manière optimale.

## Qu'est-ce que la chlorose ferrique ?

La chlorose ferrique est une carence en fer qui se manifeste par un jaunissement des feuilles jeunes, les nervures restant vertes. Elle affecte particulièrement les cultures en sols calcaires.

## FERROLAS 4.9 : La solution

### Caractéristiques
- Fer (Fe) chélaté par EDDHA : 6%
- Isomère Orto-Orto (O-O) : 4.9%
- Haute solubilité et réponse rapide
- Action prolongée

### Avantages
- Efficace même en sols à pH élevé
- Résistant aux fortes concentrations en Ca et Na
- Assimilation rapide par les plantes
- Action de longue durée

## Mode d'application

### Dosage recommandé
- Application au sol : 1.5 à 2 kg/ha
- En fonction de la gravité de la carence
- 2 à 3 applications par saison

### Conseils d'utilisation
1. Appliquer en début de saison
2. Irriguer après l'application
3. Renouveler si nécessaire après 4-6 semaines
4. Adapter le dosage selon l'analyse de sol

## Cultures concernées

Particulièrement recommandé pour :
- Arbres fruitiers (agrumes, pommiers)
- Cultures maraîchères (tomate, concombre)
- Vignes
- Cultures ornementales

Pour plus d'informations techniques, contactez nos agronomes.`,
        date: new Date('2024-12-05'),
        author: 'Service Technique',
        category: 'Technique',
        tags: ['Correcteurs', 'Technique', 'Guide'],
        readTime: 6
      },
      {
        id: '5',
        title: 'Journée Portes Ouvertes - Mars 2025',
        excerpt: 'Venez découvrir nos installations et rencontrer nos experts lors de notre journée portes ouvertes annuelle.',
        content: `Atlas Horticole vous invite à sa journée portes ouvertes annuelle qui se tiendra en mars 2025. Une occasion unique de découvrir nos installations et d'échanger avec nos experts.

## Au programme

### Visites guidées
- Découverte de nos installations
- Présentation de nos processus de production
- Démonstrations terrain

### Conférences techniques
- 10h00 : Nouvelles solutions en biostimulation
- 14h00 : Lutte intégrée : retours d'expériences
- 16h00 : Agriculture de demain

### Ateliers pratiques
- Identification des carences
- Application des correcteurs
- Mise en place de la lutte biologique

## Informations pratiques

- **Date** : Mars 2025 (date à confirmer)
- **Lieu** : Site Atlas Horticole, Ait Amira
- **Horaires** : 9h00 - 18h00
- **Entrée gratuite** sur inscription

## Inscription obligatoire

Places limitées - Inscrivez-vous dès maintenant :
- Par téléphone : 0665 63 07 27
- Par email : horticole.atlas@gmail.com

Des rafraîchissements et un repas seront offerts aux participants.`,
        date: new Date('2024-12-01'),
        author: 'Communication Atlas Horticole',
        category: 'Événements',
        tags: ['Événement', 'Portes ouvertes', 'Formation'],
        readTime: 3
      },
      {
        id: '6',
        title: 'Engrais hydrosolubles : Optimisez vos apports NPK',
        excerpt: 'Comment choisir et utiliser efficacement notre gamme d\'engrais hydrosolubles ATLAS selon les phases de culture.',
        content: `Notre gamme d'engrais hydrosolubles ATLAS offre des formulations adaptées à chaque phase de développement de vos cultures. Voici comment les utiliser efficacement.

## Gamme ATLAS : 4 formulations

### ATLAS 13-40-13 TE
**Phase : Démarrage et repiquage**
- Riche en phosphore pour l'enracinement
- Idéal pour le vivier et la préfloraison
- Stimule la nouaison

### ATLAS 20-20-20 TE
**Phase : Croissance active**
- Formulation équilibrée
- Fructification et croissance des fruits
- Phases actives de la culture

### ATLAS 15-05-30 TE
**Phase : Développement végétatif**
- Favorise la croissance de la culture
- Développement des fruits
- Riche en potassium

### ATLAS 07-12-40-2-MGO TE
**Phase : Maturation**
- Favorise la maturation des fruits
- Améliore le calibre et la qualité
- Enrichi en magnésium

## Avantages de la gamme

- **Sans chlore** : Convient aux cultures sensibles
- **Microéléments chélatés** : Disponibilité optimale
- **Haute solubilité** : Application facile
- **Équilibre adapté** : Pour chaque phase

## Conseils d'application

1. Adapter la formule selon la phase de culture
2. Respecter les dosages recommandés
3. Assurer une bonne dissolution
4. Combiner avec d'autres fertilisants si nécessaire

Contactez nos techniciens pour un programme personnalisé.`,
        date: new Date('2024-11-28'),
        author: 'Département Agronomie',
        category: 'Technique',
        tags: ['Engrais', 'Technique', 'Nutrition'],
        readTime: 5
      }
    ];

    this.featuredArticle = this.articles.find(a => a.featured);
  }

  handleCategoryChange(category: string): void {
    this.selectedCategory = category;
    this.applyFilters();
  }

  handleSearchChange(query: string): void {
    this.searchQuery = query;
    this.applyFilters();
  }

  private applyFilters(): void {
    this.filteredArticles = this.articles.filter(article => {
      const matchesCategory = this.selectedCategory === 'Tous' || article.category === this.selectedCategory;
      const matchesSearch = !this.searchQuery ||
        article.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        article.excerpt.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        article.tags?.some(tag => tag.toLowerCase().includes(this.searchQuery.toLowerCase()));

      return matchesCategory && matchesSearch;
    });
  }

}
