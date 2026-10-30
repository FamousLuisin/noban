import LoginForm from "@/components/LoginForm";
import { Button } from "@/components/ui/button";
import { ChevronLeft } from "lucide-react";
import { Link } from "react-router";

export default function Login(){
    return(
        <div className="flex flex-col justify-center px-5 gap-10">
            <div className="flex gap-3 items-center text-xl">
                <Link to="/"><Button className="cursor-pointer"><ChevronLeft /></Button></Link>Go to back
            </div>
            <div className="flex flex-col px-1 gap-4">
                <h1 className="text-3xl text-primary text-start">Login</h1>
                <LoginForm />
            </div>
            <div>
                Don't have an account?<Link to="/register"><Button variant="link" className="text-lg font-bold cursor-pointer">Sign Up</Button></Link>
            </div>
        </div>
    )
}