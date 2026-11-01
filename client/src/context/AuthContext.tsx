import { api, NAME_KEY, TOKEN_KEY } from "@/services/api";
import type { LoginDTO, AuthResponse, RegisterDTO } from "@/types/AuthType";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";
import React, { createContext, useContext, useEffect, useState } from "react";

interface AuthContextInterface {
    token: string | null
    name: string | null
    authRegister: () => UseMutationResult<AuthResponse, Error, RegisterDTO, unknown>
    authLogin: () => UseMutationResult<AuthResponse, Error, LoginDTO, unknown>
    logout: () => void
    isLoggedIn: () => boolean
}

interface PropsContext { children: React.ReactNode }

const AuthContext = createContext<AuthContextInterface>({} as AuthContextInterface)

export const AuthProvider = ({ children }: PropsContext) => {
    const [token, setToken] = useState<string | null>(null);
    const [name, setName] = useState<string | null>(null);
    const [isReady, setIsReady] = useState<boolean>(false)

    useEffect(() => {
        const token = localStorage.getItem(TOKEN_KEY);
        const name = localStorage.getItem(NAME_KEY);

        if(token && name){
            setToken(token)
            setName(name)
        }

        setIsReady(true)
    }, [])

    async function login(credentials: LoginDTO): Promise<AuthResponse> {
        const response = await api.post<AuthResponse>("/auth/login", credentials)
        
        if(response.status == 200) {
            localStorage.setItem(TOKEN_KEY, response.data.token)
            setToken(response.data.token)
            localStorage.setItem(NAME_KEY, response.data.name)
            setName(response.data.name)
        }

        return response.data
    }

    function authLogin(){
        return useMutation({
                mutationFn: login,
        })
    }

    async function register(credentials: RegisterDTO): Promise<AuthResponse> {
        const response = await api.post<AuthResponse>("/auth/register", credentials)
        
        if(response.status == 200) {
            localStorage.setItem(TOKEN_KEY, response.data.token)
            setToken(response.data.token)
            localStorage.setItem(NAME_KEY, response.data.name)
            setName(response.data.name)
        }

        return response.data
    }

    function authRegister(){
        return useMutation({
                mutationFn: register,
        })
    }

    const isLoggedIn = () => {
        return localStorage.getItem(TOKEN_KEY) !== null
    }

    const logout = () => {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(NAME_KEY);
        setToken(null)
        setName(null)
    }

    return (
        <AuthContext.Provider value={{ token, name, authLogin, logout, isLoggedIn, authRegister }}>
            {isReady ? children : null}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (context === undefined)
    throw new Error("useTheme must be used within a AuthProvider");

  return context;
};
