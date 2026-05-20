
interface searchProps {
    pokemon: string;
    setPokemon: (value: string) => void;
    handleMatch: () => void;
}

export default function SearchPanel({pokemon, setPokemon, handleMatch}: searchProps) {

    return (
        <div className="card text-center">
            <h3 className="card-header">Pokémon Floss Matcher</h3>
            <div className="card-body">

                <p className="card-text">Enter a Pokémon's name to get a list of matching DMC flosses!</p>
                <input
                    type="text"
                    value={pokemon}
                    onChange={(e) => setPokemon(e.target.value)}
                />
                <button onClick={handleMatch}>
                    Match
                </button>
            </div>
        </div>
    );
}