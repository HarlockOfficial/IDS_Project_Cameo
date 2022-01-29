import { Ombrellone } from "./ombrellone";
import { Prenotazione } from "./prenotazione";

export interface PrenotazioneSpiaggia {
    lettini: number;
    sdraio: number;
    startDate: Date;
    endDate: Date;
    ombrellone: Ombrellone;
    prenotazione?: Prenotazione;
}
