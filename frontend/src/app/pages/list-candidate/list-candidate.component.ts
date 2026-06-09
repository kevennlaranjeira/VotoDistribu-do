import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CandidateComponent } from '../../components/candidate/candidate.component';
import { FormsModule } from '@angular/forms';
import { apiBase, apiCore } from '../../service/api';
import SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { isTokenValid } from '../../utils/auth.utils';

export interface Candidato {
  id: number;
  nome: string;
  quantidadeVotos: number;
  porcentagem: number;
}

@Component({
  selector: 'app-list-candidate',
  standalone: true,
  imports: [CommonModule, CandidateComponent, FormsModule],
  templateUrl: './list-candidate.component.html',
  styleUrls: ['./list-candidate.component.css'],
})
export class ListCandidateComponent implements OnInit, OnDestroy {
  candidatos: Candidato[] = [];
  stompClient: Client;

  isConnected = false;
  isConnecting = false;
  error: string | null = null;
  hit: string | null = null;

  mostrarModal = false;
  isSubmitting = false;

  constructor(private router: Router) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8081/ws'),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });
  }

  ngOnInit(): void {
    this.conectarWebSocket();
    this.carregarCandidatosIniciais();
  }

  ngOnDestroy(): void {
    this.desconectarWebSocket();
  }

  conectarWebSocket(): void {
    if (this.isConnecting || this.isConnected) {
      return;
    }

    this.isConnecting = true;

    // Configurar callbacks antes de ativar
    this.stompClient.onConnect = () => {
      this.hit = 'Conectado ao WebSocket';
      this.isConnected = true;
      this.isConnecting = false;
      this.error = null;

      this.stompClient.subscribe('/topic/candidatos', (message: Message) => {
        try {
          this.candidatos = JSON.parse(message.body);
        } catch (error) {
          this.error = 'Erro ao processar dados recebidos';
        }
      });
    };

    this.stompClient.onStompError = (frame) => {
      this.error = `Erro de conexão WebSocket: ${frame.headers['message'] || 'Erro desconhecido'}`;
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.onWebSocketError = (event) => {
      this.error = 'Erro de conexão WebSocket';
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.onDisconnect = () => {
      this.isConnected = false;
      this.isConnecting = false;
    };

    this.stompClient.activate();
  }

  desconectarWebSocket(): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.deactivate();
    }
    this.isConnected = false;
    this.isConnecting = false;
  }

  async carregarCandidatosIniciais(): Promise<void> {
    try {
      const response = await apiCore.get('/eleicao-gp2/listarCandidatosDesc');
      this.candidatos = Array.isArray(response.data) ? response.data : [];
    } catch (error) {
      this.candidatos = [];
      this.error = 'Erro ao buscar candidatos';
    }
  }

  votar(candidato: Candidato): void {
    if (this.logout_no_token()) return;

    const token = localStorage.getItem('token');

    try {
      apiBase.post(
        `/eleicao-gp2/votar/${candidato.id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      this.hit = `Votou no candidato: ${candidato.nome}`;
    } catch (error) {
      this.error = 'Erro ao votar';
    }
  }

  logout_no_token(): boolean {
    const token = localStorage.getItem('token');

    if (!token || !isTokenValid(token)) {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
      alert('Deslogado');
      return true;
    }

    return false;
  }

  returnNick() {
    const token = localStorage.getItem('token');
    if (!token) return;
    const decoded: any = jwtDecode(token);
    return decoded.nick;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
    alert('Faça o login novamente');
    return;
  }
}
