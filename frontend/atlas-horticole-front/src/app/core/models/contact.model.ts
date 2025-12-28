export interface ContactForm {
    name: string;
    email: string;
    phone: string;
    company?: string;
    subject: string;
    message: string;
}

export interface ContactInfo {
    title: string;
    icon: string;
    value: string;
    link?: string;
}