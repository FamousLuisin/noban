import { Button } from "@/components/ui/button"

export default function Header(){
    return (
        <div className="grid grid-cols-3 pt-3 gap-3">
            <h1 className="text-4xl font-bold flex items-center">
                <Button variant="link" className="text-secondary text-4xl hover:no-underline cursor-pointer">Noban</Button>
            </h1>
            <ul className="flex gap-2 justify-center items-center">
                <li><Button variant="link" className="text-secondary text-md">About</Button></li>
                <li><Button variant="link" className="text-secondary text-md">Objetives</Button></li>
                <li><Button variant="link" className="text-secondary text-md">Price</Button></li>
            </ul>
            <div className="flex justify-end items-center">
                <Button className="p-4 bg-blue-500 hover:bg-blue-600 cursor-pointer">Get started</Button>
            </div>
            <hr className="col-span-3"/>
        </div>
    )
}