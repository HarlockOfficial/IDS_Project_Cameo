import { Evento } from "./evento";
import { PrenotazioneSpiaggia } from "./prenotazioneSpiaggia";
import { User } from "./user";
import { StatoPrenotazione } from "./StatoPrenotazione";

export interface Prenotazione {
    id?: string;
    user: User;
    statoPrenotazione: StatoPrenotazione;
    date: Date;
    eventiPrenotatiList?: Evento[];
    spiaggiaPrenotazioniList?: PrenotazioneSpiaggia[];
}
