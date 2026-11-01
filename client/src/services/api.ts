import axios from "axios"

export const TOKEN_KEY = "NobanToken"
export const NAME_KEY = "NobanName"

export const api = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        'Content-Type': 'application/json'
    }
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem(TOKEN_KEY);
  
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});