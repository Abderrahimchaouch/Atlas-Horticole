import { Component, OnInit } from '@angular/core';
import { ContactInfo, FooterSection } from '../presenter/footer-pres.interfaces';

@Component({
  selector: 'app-footer-cont',
  standalone: false,
  templateUrl: './footer-cont.component.html',
  styleUrl: './footer-cont.component.scss'
})

export class FooterContComponent implements OnInit {
  currentYear = new Date().getFullYear();

  footerSections: FooterSection[] = [
    {
      title: 'Nos Produits',
      links: [
        { label: 'Biostimulants', route: '/produits' },
        { label: 'Correcteurs de Carences', route: '/produits' },
        { label: 'Engrais Hydrosolubles', route: '/produits' },
        { label: 'Lutte Intégrée', route: '/produits' },
        { label: 'Produits Spéciaux', route: '/produits' }
      ]
    },
    {
      title: 'Articles',
      links: [
        { label: 'Innovation', route: '/articles' },
        { label: 'Formation', route: '/articles' },
        { label: 'Entreprise', route: '/articles' },
        { label: 'Événements', route: '/articles' }
      ]
    },
    {
      title: 'À Propos',
      links: [
        { label: 'Qui sommes-nous ?', route: '/a-propos', fragment: 'about-hero' },
        { label: 'Nos Valeurs', route: '/a-propos', fragment: 'about-values' },
        { label: 'Notre Histoire', route: '/a-propos', fragment: 'about-story' },
      ]
    },
    {
      title: 'Ressources',
      links: [
        { label: 'FAQ', route: '/faq' },
        { label: 'Catalogue', route: '/catalogue' }
      ]
    }
  ];

  contactInfo: ContactInfo = {
    phone: ['0665630727', '0661323022'],
    email: 'horticole.atlas@gmail.com',
    address: 'Lotissement Iskan n° 129, Ait Amira, Chtouka Ait Baha'
  };

  ngOnInit(): void {

  }
}
