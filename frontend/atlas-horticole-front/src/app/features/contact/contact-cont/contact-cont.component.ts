import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactForm, ContactInfo } from './contact.model';

@Component({
  selector: 'app-contact-cont',
  standalone: false,
  templateUrl: './contact-cont.component.html',
  styleUrl: './contact-cont.component.scss'
})
export class ContactContComponent implements OnInit {
  contactForm!: FormGroup;
  isSubmitting = false;
  submitSuccess = false;
  submitError = '';

  contactInfos: ContactInfo[] = [
    {
      title: 'Téléphone',
      icon: 'phone',
      value: '0665 63 07 27',
      link: 'tel:0665630727'
    },
    {
      title: 'Téléphone 2',
      icon: 'phone',
      value: '0661 32 30 22',
      link: 'tel:0661323022'
    },
    {
      title: 'Email',
      icon: 'email',
      value: 'horticole.atlas@gmail.com',
      link: 'mailto:horticole.atlas@gmail.com'
    },
    {
      title: 'Adresse',
      icon: 'location',
      value: 'Lotissement Iskan n° 129, Ait Amira, Chtouka Ait Baha',
      link: 'https://maps.google.com/?q=Ait+Amira+Chtouka+Ait+Baha'
    }
  ];

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      company: [''],
      subject: ['', [Validators.required]],
      message: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  handleSubmit(): void {
    if (this.contactForm.invalid) {
      this.contactForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.submitError = '';
    this.submitSuccess = false;

    const formData: ContactForm = this.contactForm.value;

    // Simuler l'envoi (remplacer par votre service)
    this.submitContactForm(formData);
  }

  private submitContactForm(data: ContactForm): void {
    // TODO: Remplacer par votre service HTTP
    // this.contactService.sendMessage(data).subscribe(...)

    // Simulation
    setTimeout(() => {
      this.isSubmitting = false;
      this.submitSuccess = true;
      this.contactForm.reset();

      // Masquer le message de succès après 5 secondes
      setTimeout(() => {
        this.submitSuccess = false;
      }, 5000);
    }, 1500);
  }
}
