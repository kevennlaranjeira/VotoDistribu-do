import axios from 'axios';

export const apiCore = axios.create({
  baseURL: 'http://localhost:8081',
});

export const apiBase = axios.create({
  baseURL: 'http://coletor.local:8080', // por enquanto, fazer o front trocar
});
