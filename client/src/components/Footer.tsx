import { Copyright } from "lucide-react";
import logo from "@/assets/logo.png"
import linkedin from "@/assets/linkedin.png"
import github from "@/assets/github.png"
import gmail from "@/assets/gmail.png"

export default function Footer(){
    return (
        <div className="grid grid-cols-3 gap-3 w-full py-2">
            <hr className="w-full col-span-3"/>
            <div><img src={logo} alt="" className="w-8"/></div>
            <div className="flex items-center justify-center gap-2"><Copyright /> Noki</div>
            <div className="flex flex-col items-end justify-center">
                <ul className="flex items-center gap-5">
                    <li>
                        <a href="https://www.linkedin.com/in/luis-filipe-melo-de-miranda/" target="_blank"  className="hover:text-blue-400">
                            <img src={linkedin} alt="" className="w-8"/>
                        </a>
                    </li>
                    <li>
                        <a href="https://github.com/FamousLuisin" target="_blank"  className="hover:text-blue-400">
                            <img src={github} alt="" className="w-8"/>
                        </a>
                    </li>
                    <li>
                        <a href="mailto:lufizinmelo@gmail.com" className="hover:text-blue-400">
                            <img src={gmail} alt="" className="w-8"/>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    )
}