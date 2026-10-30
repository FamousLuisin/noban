import { createBrowserRouter } from "react-router"
import App from "./pages/App"
import LayoutAuth from "./components/LayoutAuth"
import Login from "./pages/Login"
import Register from "./pages/Register"

const routes = createBrowserRouter([
    {
        path: "/",
        Component: App
    },
    {
        Component: LayoutAuth,
        children: [{
            path: "/login",
            Component: Login
        },
        {
            path: "/register",
            Component: Register
        }
    ]
    }
])

export default routes