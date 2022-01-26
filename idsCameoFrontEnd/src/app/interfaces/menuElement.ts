import { MenuSection } from "./menuSection";

export interface MenuElement {
    name: string;
    description: string;
    price: number;
    section: MenuSection;
    isElementVisible: boolean;
}
