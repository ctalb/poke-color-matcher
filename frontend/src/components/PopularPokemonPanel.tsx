import type { PopularPokemon } from "../types";
import {useEffect, useState} from "react";
import BASE_URL from "../config.ts";

export default function PopularPokemonPanel() {
    const [popularPokemon, setPopularPokemon] = useState<PopularPokemon[]>([]);

    useEffect(() => {
            async function fetchPopularPokemon() {
                try {
                    const response = await fetch(`${BASE_URL}/api/pokemon/popular`);
                    const data = await response.json();
                    setPopularPokemon(data);
                } catch (error) {
                    console.error("Error fetching popular Pokemon:", error);
                }
            }
            fetchPopularPokemon();
        }, []);

    return (
        <div>
            <h5>Top 3 Pokemon Searched</h5>
            <ol>
                {popularPokemon.map((pokemon) => (
                    <li key={pokemon.pokemonName}>{pokemon.pokemonName.split('-')
                        .map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ')}</li>
                ))}
            </ol>
        </div>
    );
}