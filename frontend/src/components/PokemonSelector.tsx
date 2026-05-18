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
    const [red, green, blue] = flossColorMatch.extractedColor;
    const backgroundColor = `rgba(${red}, ${green}, ${blue})`;

    return (
        <>
            <div style={{
                backgroundColor,
                width: "20px",
                height: "20px",
            }}
            ></div>
            <p>Floss Number: {flossColorMatch.match.number}</p>
            <p>Name: {flossColorMatch.match.name}</p>
        </>
    );
}

export default function PokemonSelector() {
    const [pokemon, setPokemon]= useState("");
    const [matchResult, setMatchResult]= useState<MatchResult | null>(null);
    const [isError, setIsError] = useState<boolean>(false);

    async function handleMatch() {

        const url=`http://localhost:8080/api/pokemon/${pokemon}/match`;
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
            <input
                type="text"
                value={pokemon}
                onChange={(e) => setPokemon(e.target.value)}
            />

            <button onClick={handleMatch}>
                Match
            </button>

            <h2>{isError && "Pokémon does not exist! Please check your spelling and try again."}</h2>

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