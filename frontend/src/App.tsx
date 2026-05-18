import SearchPanel from "./components/SearchPanel.tsx";
import ResultsPanel from "./components/ResultsPanel.tsx";
import {useState} from "react";
import type {MatchResult} from "./types.ts";



function App() {
    const [pokemon, setPokemon]= useState("");
    const [matchResult, setMatchResult]= useState<MatchResult | null>(null);
    const [isError, setIsError] = useState<boolean>(false);

    async function handleMatch() {

        const url=`http://localhost:8080/api/pokemon/${encodeURIComponent(pokemon)}/match`;
        setMatchResult(null);
        setIsError(false);

        try {
            const response = await fetch(url);
            if (!response.ok) {
                setIsError(true);
                throw new Error(`Response status: ${response.status}`);
            }

            const result = await response.json();
            setMatchResult(result);

        } catch (error) {
            if (error instanceof Error) {
                console.error(error.message);
            } else {
                console.error("Unknown error", error);
            }
        }
    }

  return (
      <>
        <SearchPanel
            pokemon={pokemon}
            setPokemon={setPokemon}
            handleMatch={handleMatch}
        />
        <ResultsPanel
            matchResult={matchResult}
            isError={isError}
        />
      </>
    );
}

export default App
