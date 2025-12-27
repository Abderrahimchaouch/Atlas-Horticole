import { Component, OnInit } from '@angular/core';
import { AboutSection, Statistic, TimelineItem, ValueItem } from './about.model';

@Component({
  selector: 'app-about-cont',
  standalone: false,
  templateUrl: './about-cont.component.html',
  styleUrl: './about-cont.component.scss'
})
export class AboutContComponent implements OnInit {
  mainSection: AboutSection = {
    title: 'Atlas Horticole - La nature pour partenaire',
    content: `Inspirés par la majesté des montagnes de l'Atlas, nous développons des solutions agricoles innovantes qui respectent l'environnement et optimisent vos rendements. Notre mission est d'accompagner les agriculteurs vers une agriculture durable et performante.

Depuis notre création, nous nous engageons à fournir des produits de haute qualité, alliant innovation technologique et respect de la nature. Notre équipe d'experts agronomiques travaille quotidiennement pour vous offrir les meilleures solutions adaptées à vos besoins.`
  };

  values: ValueItem[] = [
    {
      icon: 'quality',
      title: 'Qualité & Excellence',
      description: 'Des produits certifiés et rigoureusement contrôlés pour garantir les meilleurs résultats pour vos cultures.'
    },
    {
      icon: 'innovation',
      title: 'Innovation Continue',
      description: 'Recherche et développement constants pour vous proposer des solutions agricoles à la pointe de la technologie.'
    },
    {
      icon: 'sustainability',
      title: 'Agriculture Durable',
      description: 'Engagement fort pour des pratiques agricoles respectueuses de l\'environnement et de la biodiversité.'
    },
    {
      icon: 'expertise',
      title: 'Expertise Technique',
      description: 'Une équipe d\'agronomes qualifiés à votre service pour vous conseiller et vous accompagner.'
    },
    {
      icon: 'trust',
      title: 'Confiance & Proximité',
      description: 'Relation de confiance basée sur l\'écoute, la transparence et la disponibilité de nos équipes.'
    },
    {
      icon: 'results',
      title: 'Résultats Garantis',
      description: 'Solutions éprouvées qui ont fait leurs preuves auprès de centaines d\'agriculteurs satisfaits.'
    }
  ];

  statistics: Statistic[] = [
    { value: '10+', label: 'Années d\'expérience', icon: 'calendar' },
    { value: '50+', label: 'Produits disponibles', icon: 'package' },
    { value: '500+', label: 'Clients satisfaits', icon: 'users' },
    { value: '100%', label: 'Engagement qualité', icon: 'award' }
  ];

  timeline: TimelineItem[] = [
    {
      year: '2014',
      title: 'Création d\'Atlas Horticole',
      description: 'Lancement de l\'entreprise avec une vision claire : proposer des solutions agricoles innovantes et durables.'
    },
    {
      year: '2016',
      title: 'Expansion de la gamme',
      description: 'Introduction des biostimulants organiques et développement de la gamme correcteurs de carences.'
    },
    {
      year: '2018',
      title: 'Certification qualité',
      description: 'Obtention des certifications internationales attestant de la qualité de nos produits et processus.'
    },
    {
      year: '2020',
      title: 'Lutte intégrée',
      description: 'Lancement de notre gamme complète de solutions de biocontrôle et de pollinisation naturelle.'
    },
    {
      year: '2023',
      title: 'Innovation & Croissance',
      description: 'Développement de nouveaux produits spéciaux et renforcement de notre présence au Maroc.'
    },
    {
      year: '2024',
      title: 'Aujourd\'hui',
      description: 'Leader reconnu dans les solutions agricoles durables avec une clientèle en constante croissance.'
    }
  ];

  ngOnInit(): void {
    // Load data from service if needed
  }

}
