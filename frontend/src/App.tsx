import SearchPanel from "./components/SearchPanel.tsx";
import ResultsPanel from "./components/ResultsPanel.tsx";
import {useState} from "react";
import type {MatchResult} from "./types.ts";

function App() {
    const [pokemon, setPokemon]= useState("");
    const [hasSearched, setHasSearched] = useState<boolean>(false);
    const [matchResult, setMatchResult]= useState<MatchResult | null>(null);
    const [isError, setIsError] = useState<boolean>(false);

    async function handleMatch() {

        setHasSearched(true);

        const url=`http://localhost:8080/api/pokemon/${encodeURIComponent(pokemon.trim())}/match`;
        setMatchResult(null);
        setIsError(false);

        if (!pokemon.trim()) {
            setIsError(true);
            setMatchResult(null);
            return;
        }

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
      <div className="container">
          {!hasSearched ? (
              <div className="row justify-content-center">
                  <div className="col-4">
                      <SearchPanel
                          pokemon={pokemon}
                          setPokemon={setPokemon}
                          handleMatch={handleMatch}
                      />
                  </div>
              </div>
          ) : (
              <div className="row">
                  <div className="col-4">
                      <SearchPanel
                          pokemon={pokemon}
                          setPokemon={setPokemon}
                          handleMatch={handleMatch}
                      />
                  </div>
                  <div className="col-8">
                      <ResultsPanel
                          matchResult={matchResult}
                          isError={isError}
                      />
                  </div>
              </div>
          )}
      </div>
    );
}

export default App
