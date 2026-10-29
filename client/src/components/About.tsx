import sticky from "@/assets/Colorful Sticky Notes.png"

export default function About(){
    return (
        <div className="flex flex-1" id="about">
            <div className="absolute w-3/5 h-3/5 bg-gray-200 right-0 bottom-0 rounded-3xl "></div>
            <img src={sticky} className="absolute w-3/5 h-3/5 right-32 bottom-32 object-contains rounded-3xl [@media(max-height:920px)]:right-0 [@media(max-height:920px)]:bottom-0 max-lg:w-full" />
            <div className="max-w-5xl rounded-xl flex flex-col items-center gap-8 z-10">
                <h1 className="text-7xl font-bold text-primary max-sm:text-6xl">Transforme suas ideias em <span className="text-gray-700">realidade</span></h1>
            </div>
        </div>
    )
}