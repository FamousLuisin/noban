import { Button } from "@/components/ui/button"
import logo from "@/assets/logo.png"

export default function Header(){
    return (
        <div className="grid grid-cols-3 pt-3 gap-3">
            <h1 className="text-4xl font-bold flex items-center max-sm:col-span-2">
                <Button variant="link" className="text-primary-text text-3xl hover:no-underline cursor-pointer">
                    <img src={logo} alt="" className="flex w-12"/>Noban
                </Button>
            </h1>
            <ul className="flex gap-2 justify-center items-center max-sm:hidden">
                <li><Button variant="link" className="text-primary-text text-md"><a href="#about">Sobre</a></Button></li>
                <li><Button variant="link" className="text-primary-text text-md"><a href="#details">Detalhes</a></Button></li>
            </ul>
            <div className="flex justify-end items-center gap-3">
                <Button variant="secondary" className="p-4 cursor-pointer">Login</Button>
                <Button className="p-4 cursor-pointer">Regristre</Button>
            </div>
            <hr className="col-span-3"/>
        </div>
    )
}