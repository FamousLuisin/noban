import Header from "../components/Header";
import About from "../components/About";
import Details from "../components/Details";
import Footer from "../components/Footer";

function App() {
  return (
    <main className="min-h-screen w-full grid grid-cols-1 gap-5">
      <div className="min-h-screen flex flex-col gap-5 px-7">
        <Header />
        <About />
      </div>
      <div className="flex flex-col gap-3 min-h-screen px-7 py-2">
        <Details />
        <Footer />
      </div>
    </main>
  );
}

export default App
