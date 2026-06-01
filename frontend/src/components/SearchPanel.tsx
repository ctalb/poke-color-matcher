
interface SearchProps {
    pokemon: string;
    setPokemon: (value: string) => void;
    handleMatch: () => void;
    isLoading: boolean;
}

export default function SearchPanel({pokemon, setPokemon, handleMatch, isLoading}: SearchProps) {

    return (
        <div className="card text-center">
            <h3 className="card-header">Pokémon Floss Matcher</h3>
            <div className="card-body">

                <p className="card-text">Enter a Pokémon's name to get a list of matching DMC flosses!</p>
                <form onSubmit={e => {
                    e.preventDefault();
                    handleMatch();
                }}>
                    <div className="input-group mb-3">
                        <input
                            className="form-control"
                            type="text"
                            value={pokemon}
                            onChange={(e) => setPokemon(e.target.value)}
                        />
                        <button
                            className="btn btn-primary"
                            type="submit"
                            disabled={isLoading}>
                            Match
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}