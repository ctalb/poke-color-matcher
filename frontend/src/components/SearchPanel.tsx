
interface searchProps {
    pokemon: string;
    setPokemon: (value: string) => void;
    handleMatch: () => void;
}

export default function SearchPanel({pokemon, setPokemon, handleMatch}: searchProps) {

    return (
        <>
            <h2>Pokémon Floss Matcher</h2>
            <p>Enter a Pokémon's name to get a list of matching DMC flosses!</p>
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