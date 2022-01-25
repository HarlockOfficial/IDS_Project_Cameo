import { Component, OnInit } from '@angular/core';
import { EventiService } from '../_services/eventi.service';
import { Evento } from '../interfaces/evento';
@Component({
  selector: 'app-eventi',
  templateUrl: './eventi.component.html',
  styleUrls: ['./eventi.component.css']
})
export class EventiComponent implements OnInit {

  listaEventi!: Evento[];

  constructor(private eventiService: EventiService) { }

  ngOnInit(): void {
    this.onGetAllEvents();
  }

  onGetAllEvents(): void {
    this.eventiService.allEventi()?.subscribe(
      data => {
        this.listaEventi = data as Evento[];
        console.log(this.listaEventi);
      }
    );
  }

}
