import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ContactInfo } from '../../../../core/models/contact.model';

@Component({
  selector: 'app-contact-pres',
  standalone: false,
  templateUrl: './contact-pres.component.html',
  styleUrl: './contact-pres.component.scss'
})
export class ContactPresComponent {
  @Input() contactForm!: FormGroup;
  @Input() contactInfos: ContactInfo[] = [];
  @Input() isSubmitting: boolean = false;
  @Input() submitSuccess: boolean = false;
  @Input() submitError: string = '';

  @Output() formSubmit = new EventEmitter<void>();

  onSubmit(): void {
    if (this.contactForm.valid) {
      this.formSubmit.emit();
    }
  }
}
