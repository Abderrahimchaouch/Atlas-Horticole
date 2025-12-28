export interface CatalogDocument {
    id: string;
    title: string;
    description: string;
    fileType: 'pdf' | 'excel' | 'word';
    fileSize: string;
    pages?: number;
    category: string;
    year: string;
    imageUrl?: string;
    downloadUrl: string;
    featured?: boolean;
}

export interface DownloadForm {
    name: string;
    email: string;
    company: string;
    phone: string;
    acceptTerms: boolean;
}