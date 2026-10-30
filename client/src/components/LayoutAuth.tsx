import { Outlet } from "react-router"
import logo from "@/assets/logo.png"

export default function LayoutAuth(){
    return (
        <div className="w-full grid grid-cols-[2fr_1fr] min-h-screen max-[920px]:grid-cols-1">
            <div className="flex flex-col justify-center items-end px-5 bg-[url(./assets/AuthWallpaperMinimalistc.jpg)] bg-cover bg-no-repeat bg-center">
                <div className="w-1/2 min-w-96 flex flex-col gap-5 max-[920px]:hidden">
                    <div className="flex text-lg items-end text-secondary">
                        <img src={logo} alt="" className="invert w-8 h-8"/>Noban
                    </div>
                   <div className="flex flex-col gap-3">
                        <h1 className="text-6xl text-secondary">
                            Build Better Projects.<br />Achieve More Together.
                        </h1>
                        <p className="text-secondary">
                            Organize your workflow, collaborate with your team, and turn great ideas into meaningful results. 
                            Bring your projects to life with a smarter way to work.
                        </p>
                   </div>
                </div>
            </div>
            <Outlet />
        </div>
    )
}