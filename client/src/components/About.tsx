import sticky from "@/assets/Kanban.jpg"

export default function About(){
    return (
        <div className="grid grid-rows-[auto_1fr] flex-1" id="about">
            <div className="max-w-5xl rounded-xl flex flex-col items-center gap-8 z-10">
                <h1 className="text-7xl font-bold text-primary max-sm:text-6xl">Transforme suas ideias em <span className="text-gray-700">realidade</span></h1>
            </div>
            <div className="relative w-full h-full">
                <div className="absolute w-3/5 h-3/4 bg-gray-700 right-0 bottom-0 rounded-3xl"></div>
                <img src={sticky} className="
                    absolute 
                    w-3/5 h-3/4 
                    right-24 bottom-24 
                    object-contains 
                    rounded-3xl 

                    max-[1080px]:right-0
                    max-[1080px]:bottom-0
                    max-[1080px]:w-full
                    max-[1080px]:h-full

                    max-[1360px]:right-16
                    max-[1360px]:bottom-16
                " 
                />
            </div>
        </div>
    )
}