import { Button } from "@/components/ui/button"
import AuthDropdown from "@/components/AuthDropdown"
import logo from "@/assets/logo.png"
import { Link } from "react-router"

export default function Header(){
    return (
        <header className="fixed inset-x-0 top-0 z-50 grid grid-cols-3 py-3 bg-background px-7 rounded-b-3xl border-b-2">
            <h1 className="font-bold max-sm:col-span-2">
                <Link to="/" className="flex w-fit">
                    <Button variant="link" className="flex items-center text-primary-text text-4xl hover:no-underline cursor-pointer w-fit">
                        <img src={logo} alt="" className="flex w-12"/>Noban
                    </Button>
                </Link>
            </h1>
            <ul className="flex gap-2 justify-center items-center max-sm:hidden">
                <li><Button variant="link" className="text-primary-text text-md"><a href="#about">Sobre</a></Button></li>
                <li><Button variant="link" className="text-primary-text text-md"><a href="#details">Detalhes</a></Button></li>
            </ul>
            <div className="hidden justify-end items-center gap-3 sm:flex">
                <Link to="/login">
                    <Button variant="secondary" className="p-4 cursor-pointer">Login</Button>
                </Link>
                <Link to="/register">
                    <Button className="p-4 cursor-pointer">Register</Button>
                </Link>
            </div>
            <div className="flex justify-end items-center sm:hidden">
                <AuthDropdown />
            </div>
        </header>
    )
}
