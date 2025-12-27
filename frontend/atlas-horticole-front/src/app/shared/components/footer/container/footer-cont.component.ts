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
        { label: 'Biostimulants', route: '/produits/biostimulants' },
        { label: 'Correcteurs de Carences', route: '/produits/correcteurs' },
        { label: 'Engrais Hydrosolubles', route: '/produits/engrais' },
        { label: 'Lutte Intégrée', route: '/produits/lutte-integree' },
        { label: 'Produits Spéciaux', route: '/produits/speciaux' }
      ]
    },
    {
      title: 'Services',
      links: [
        { label: 'Consultation Agronomique', route: '/services/consultation' },
        { label: 'Programmes Personnalisés', route: '/services/programmes' },
        { label: 'Formation & Support', route: '/services/formation' },
        { label: 'Analyse de Sol', route: '/services/analyse' }
      ]
    },
    {
      title: 'À Propos',
      links: [
        { label: 'Notre Histoire', route: '/a-propos/histoire' },
        { label: 'Notre Équipe', route: '/a-propos/equipe' },
        { label: 'Certifications', route: '/a-propos/certifications' },
        { label: 'Actualités', route: '/actualites' }
      ]
    },
    {
      title: 'Ressources',
      links: [
        { label: 'Documentation Technique', route: '/ressources/documentation' },
        { label: 'Guides d\'Application', route: '/ressources/guides' },
        { label: 'FAQ', route: '/faq' },
        { label: 'Téléchargements', route: '/telechargements' }
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
