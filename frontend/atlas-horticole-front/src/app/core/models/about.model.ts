export interface AboutSection {
    title: string;
    content: string;
    imageUrl?: string;
}

export interface ValueItem {
    icon: string;
    title: string;
    description: string;
}

export interface Statistic {
    value: string;
    label: string;
    icon: string;
}

export interface TimelineItem {
    year: string;
    title: string;
    description: string;
}