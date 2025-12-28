import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    standalone: false,
    name: 'filter',
    pure: true
})
export class FilterPipe implements PipeTransform {

    transform<T>(
        items: T[],
        value: any,
        field?: keyof T
    ): T[] {
        if (!items || !value || value === 'Tous') {
            return items;
        }

        return items.filter(item => {
            if (!field) {
                return false;
            }
            return item[field] === value;
        });
    }
}
