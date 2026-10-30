
import { Button } from "@/components/ui/button";
import { ChevronLeft, UserRoundPlus } from "lucide-react";
import RegisterForm from "@/components/RegisterForm";
import { Link } from "react-router";

export default function Register(){
    return(
        <div className="flex flex-col justify-center px-5 gap-10">
            <div className="flex gap-3 items-center text-xl">
                <Link to="/"><Button className="cursor-pointer"><ChevronLeft /></Button></Link>Go to back
            </div>
            <div className="flex flex-col px-1 gap-4">
                <h1 className="text-3xl text-primary text-start flex items-center gap-2"><UserRoundPlus /> Register</h1>
                <RegisterForm />
            </div>
            <div>
                You have an account? <Link to="/login"><Button variant="link" className="text-lg font-bold cursor-pointer">Sign In</Button></Link>
            </div>
        </div>
    )
}