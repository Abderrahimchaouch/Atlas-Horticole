import { Component, OnInit } from '@angular/core';
import { CtaBanner } from '../cta-banner.model';

@Component({
  selector: 'app-cta-banner-cont',
  standalone: false,
  templateUrl: './cta-banner-cont.component.html',
  styleUrl: './cta-banner-cont.component.scss'
})
export class CtaBannerContComponent implements OnInit {
  banner: CtaBanner = {
    title: 'Besoin d\'un conseil personnalisé ?',
    description: 'Nos experts agronomiques sont à votre disposition pour vous accompagner dans le choix des meilleures solutions pour vos cultures. Profitez de notre expertise pour optimiser vos rendements.',
    primaryButton: {
      text: 'Contacter un expert',
      route: '/contact'
    },
    /*     secondaryButton: {
          text: 'Découvrir nos services',
          route: '/services'
        }, */
    imageUrl: 'images/surface-irrigation.jpg'
  };

  ngOnInit(): void {
    // Possibilité de charger depuis un service
    // this.loadBannerContent();
  }

  // private loadBannerContent(): void {
  //   this.bannerService.getBanner().subscribe(
  //     banner => this.banner = banner
  //   );
  // }

}
