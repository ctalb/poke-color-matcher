function matchButton() {
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

    return matchButton();
}