import { Ombrellone } from "./ombrellone";
import { Prenotazione } from "./prenotazione";

export interface PrenotazioneSpiaggia {
    lettini: number;
    sdraio: number;
    startDate: Date|string;
    endDate: Date|string;
    ombrellone: Ombrellone;
    prenotazione?: Prenotazione;
}
