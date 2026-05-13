import {useState} from "react";

export default function PokemonSelector() {
    const [pokemon, setPokemon]= useState("");

    function handleMatch() {
        console.log(pokemon);
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
        </>
    );
}