export interface CtaBanner {
    title: string;
    description: string;
    primaryButton: {
        text: string;
        route: string;
    };
    secondaryButton: {
        text: string;
        route: string;
    };
    imageUrl?: string;
}