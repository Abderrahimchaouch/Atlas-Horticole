import { Component, Input } from '@angular/core';
import { CtaBanner } from '../cta-banner.model';

@Component({
  selector: 'app-cta-banner-pres',
  standalone: false,
  templateUrl: './cta-banner-pres.component.html',
  styleUrl: './cta-banner-pres.component.scss'
})
export class CtaBannerPresComponent {
  @Input() banner!: CtaBanner;
}
