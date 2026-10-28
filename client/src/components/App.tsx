import Header from "./Header";
import About from "./About";

function App() {
  return (
    <main className="min-h-screen w-full grid grid-cols-1 gap-4 overflow-hidden">
      <div className="bg-[url(./assets/GlobalNetworkOriginal.png)] w-screen h-screen bg-cover bg-position-[center_70%] px-7 flex flex-col gap-5">
        <Header />
        <About />
      </div>
    </main>
  );
}

export default App
