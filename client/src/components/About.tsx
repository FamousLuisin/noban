export default function About(){
    return (
        <div className="flex flex-1 items-center px-2">
            <div className="border-2 p-4 w-3xl max-h-96 backdrop-blur-xs rounded-4xl flex flex-col items-center gap-8">
                <h1 className="text-2xl font-bold text-secondary">Transforme suas ideias em realidade</h1>
                <p className="text-center text-secondary text-xl leading-6">
                   O Noban é o espaço ideal para organizar tarefas, gerenciar projetos e transformar ideias em resultados. Com quadros Kanban e metodologias ágeis, 
                   acompanhe cada etapa do seu fluxo de trabalho, defina prioridades e mantenha sua equipe alinhada. 
                   Tenha mais clareza sobre seus objetivos, colabore de forma eficiente e avance em cada projeto com organização e propósito.
                </p>
                <p className="text-center font-bold text-secondary text-xl">Seu trabalho sempre em movimento.</p>
            </div>
        </div>
    )
}