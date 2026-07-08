import SearchPanel from "./components/SearchPanel.tsx";
import ResultsPanel from "./components/ResultsPanel.tsx";
import {useState} from "react";
import type {MatchResult} from "./types.ts";
import BASE_URL from "./config.ts";

function App() {
    const [pokemon, setPokemon]= useState("");
    const [hasSearched, setHasSearched] = useState<boolean>(false);
    const [matchResult, setMatchResult]= useState<MatchResult | null>(null);
    const [isError, setIsError] = useState<boolean>(false);
    const [isNetworkError, setIsNetworkError] = useState<boolean>(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    async function handleMatch() {

        setHasSearched(true);

        const url=`${BASE_URL}/api/pokemon/${encodeURIComponent(pokemon.trim())}/match`;
        setMatchResult(null);
        setIsError(false);
        setIsNetworkError(false);

        if (!pokemon.trim()) {
            setIsError(true);
            setMatchResult(null);
            return;
        }

        setIsLoading(true);

        const controller = new AbortController();
        const signal = controller.signal;
        const timeout = setTimeout(() => controller.abort(), 10000);

        try {
            const response = await fetch(url, { signal });

            if (!response.ok) {
                if (response.status === 503) {
                    setIsNetworkError(true);
                } else {
                    setIsError(true);
                }
                throw new Error(`Response status: ${response.status}`);
            }

            const result = await response.json();
            setMatchResult(result);

        } catch (error) {
            if (error instanceof Error) {
                if (error.name === 'AbortError') {
                    setIsNetworkError(true);
                }
                console.error(error.message);
            } else {
                console.error("Unknown error", error);
            }
        } finally {
            clearTimeout(timeout);
            setIsLoading(false);
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
                          isLoading={isLoading}
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
                          isLoading={isLoading}
                          handleMatch={handleMatch}
                      />
                  </div>
                  <div className="col-8">
                      <ResultsPanel
                          matchResult={matchResult}
                          isError={isError}
                          isNetworkError={isNetworkError}
                      />
                  </div>
              </div>
          )}
      </div>
    );
}

export default App
