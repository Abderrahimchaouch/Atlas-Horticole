export interface FooterLink {
  label: string;
  route?: string;
  fragment?:string;
  external?: boolean;
}

export interface FooterSection {
  title: string;
  links: FooterLink[];
}

export interface ContactInfo {
  phone: string[];
  email: string;
  address: string;
}