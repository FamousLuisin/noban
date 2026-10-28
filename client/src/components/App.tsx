import Header from "./Header";
import About from "./About";
import Details from "./Details";
import Footer from "./Footer";

function App() {
  return (
    <main className="min-h-screen w-full grid grid-cols-1">
      <div className="min-h-screen flex flex-col gap-5 px-7">
        <Header />
        <About />
      </div>
      <div className="flex flex-col gap-3 min-h-screen px-7 py-2">
        <div className="flex-1">
          <Details />
        </div>
        <Footer />
      </div>
    </main>
  );
}

export default App
