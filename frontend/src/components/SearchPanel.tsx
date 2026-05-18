
interface searchProps {
    pokemon: string;
    setPokemon: (value: string) => void;
    handleMatch: () => void;
}

export default function SearchPanel({pokemon, setPokemon, handleMatch}: searchProps) {

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