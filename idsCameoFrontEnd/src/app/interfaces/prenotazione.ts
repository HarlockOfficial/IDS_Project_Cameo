import { Evento } from "./evento";
import { PrenotazioneSpiaggia } from "./prenotazioneSpiaggia";
import { User } from "./user";

export interface Prenotazione {
    user: User;
    statoPrenotazione: string;
    date: Date;
    eventiPrenotatiList: Evento[];
    spiaggiaPrenotazioniList: PrenotazioneSpiaggia[];
}