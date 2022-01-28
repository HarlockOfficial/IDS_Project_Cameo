import { MenuElement } from "./menuElement";
import { Ombrellone } from "./ombrellone";
import { User } from "./user";

export interface Order {
    id?: any;
    dateTime: Date;
    orderStatus: string;
    menuElements?: MenuElement[];
    user?: User;
    ombrellone?: Ombrellone;
}
