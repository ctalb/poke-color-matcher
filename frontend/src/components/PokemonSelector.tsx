import {useState} from "react";

const BASE_URL = "http://localhost:8080";

interface DmcFloss {
    number: string;
    name: string;
    hex: string;
}

 interface FlossColorMatch {
    extractedColor: number[];
    match: DmcFloss;
}

interface MatchResult {
    name: string;
    imagePath: string;
    matches: FlossColorMatch[];
}

function ColorMatchCard({ flossColorMatch  }: {flossColorMatch: FlossColorMatch}) {
    return (
        <>
            <p>Color: [{flossColorMatch.extractedColor.join(",")}]</p>
            <p>Floss Number: {flossColorMatch.match.number}</p>
            <p>Name: {flossColorMatch.match.name}</p>
        </>
    );
}

export default function PokemonSelector() {
    const [pokemon, setPokemon]= useState("");
    const [matchResult, setMatchResult]= useState<MatchResult | null>(null);

    async function handleMatch() {
        const url=`http://localhost:8080/api/pokemon/${pokemon}/match`;

        try {
            const response = await fetch(url);
            if (!response.ok) {
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
            <input
                type="text"
                value={pokemon}
                onChange={(e) => setPokemon(e.target.value)}
            />
            <button onClick={handleMatch}>
                Match
            </button>
            {matchResult &&
                <img src={`${BASE_URL}/${matchResult.imagePath}`} alt={matchResult.name} />}
            <h2>{matchResult && matchResult.name}</h2>
            <ul>
                {matchResult && matchResult.matches.map(match =>
                    <li key={match.extractedColor.join()}>
                        <ColorMatchCard flossColorMatch={match}/>
                    </li>
                )}
            </ul>
        </>
    );
}