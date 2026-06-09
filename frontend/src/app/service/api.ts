import axios from 'axios';

export const apiCore = axios.create({
  baseURL: '/api-core',
});

export const apiBase = axios.create({
  baseURL: '/api-coletor',
});
