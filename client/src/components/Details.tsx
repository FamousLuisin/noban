import FlashCard from "./FlashCard"
import online from "@/assets/online.svg"
import organizing from "@/assets/organizing.svg"
import team from "@/assets/team.svg"

export default function Details(){
    let onlineMessage = `Acesse seus projetos de onde estiver e compartilhe suas ideias com facilidade. 
    Com tudo online, você e sua equipe podem manter o trabalho conectado, a qualquer momento e em qualquer lugar.`
    let organizingMessage = `Mantenha suas tarefas, projetos e prazos organizados em um só lugar. 
    Visualize cada etapa do trabalho com quadros Kanban e tenha mais clareza sobre o que precisa ser feito.`
    let teamMessage = `Reúna seu time em um único espaço de colaboração. 
    Compartilhe tarefas, acompanhe o progresso e mantenha todos alinhados para alcançar objetivos em conjunto.`
    return (
        <div className="flex flex-col h-full w-full items-center gap-5 py-3 scroll-mt-20 px-7" id="details">
            <h1 className="text-primary text-4xl font-bold">Detalhes</h1>
            <div className="flex flex-1 h-full w-full items-center justify-evenly max-[1200px]:flex-col gap-5">
                <FlashCard message={onlineMessage} image={online}/>
                <FlashCard message={organizingMessage} image={organizing}/>
                <FlashCard message={teamMessage} image={team}/>
            </div>
        </div>
    )
}
