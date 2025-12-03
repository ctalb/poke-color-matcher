function PokemonSearchBar() {
    return (
        <form>
            <input type="text" placeholder={"Enter Pokemon"}></input>
        </form>
    );
}

function MatchButton() {
    function handleClick() {
        console.log("matched");
    }
    return (
        <button
        onClick={handleClick}>
            Get match
        </button>
    );
}

export default function PokemonSelector() {

    return (
        <>
            <PokemonSearchBar />
            <MatchButton />
        </>

    );
}