import {useState} from "react";

export default function PokemonSelector() {
    const [pokemon, setPokemon]= useState("");
    const [matchResult, setMatchResult]= useState(null);

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
            <h2>{matchResult && matchResult.name}</h2>
        </>
    );
}