import { createRoot } from 'react-dom/client'
import './index.css'
import { RouterProvider } from "react-router/dom";
import routes from './routes';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthProvider } from './context/AuthContext';
import { ThemeProvider } from './context/ThemeContext';

const queryClient = new QueryClient();

createRoot(document.getElementById('root')!).render(
    <ThemeProvider>
        <AuthProvider>
            <QueryClientProvider client={queryClient}>
                <RouterProvider router={routes} />
            </QueryClientProvider>
        </AuthProvider>
    </ThemeProvider>
)
