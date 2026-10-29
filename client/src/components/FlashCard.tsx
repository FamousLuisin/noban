export default function FlashCard(props: {message: string, image: string}){
    return (
        <div className="flex flex-col gap-2.5 h-3/4 min-h-100 max-h-150 w-1/4 min-w-56 max-w-96">
            <div className="flex w-full h-3/5 p-2">
                <img src={props.image} alt="" className="h-full w-full bg-cover" />
            </div>
            <div className="p-2 text-lg">
                <p className="leading-6 text-justify">
                    {props.message}
                </p>
            </div>
        </div>
    )
}