import Transacao from "./pages/transacao/Transacao.tsx";
import Extrato from "./pages/extrato/Extrato.tsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/transacoes" element={<Transacao/>}/>
                <Route path="/extrato" element={<Extrato/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App
